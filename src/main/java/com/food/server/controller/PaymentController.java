package com.food.server.controller;

import com.food.server.service.RazorpayService;
import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@RestController
@RequestMapping("/api")

public class PaymentController extends BaseClass{

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> data) {
        try {
            Double amountDouble = Double.parseDouble(data.get("amount").toString());
            int amount = amountDouble.intValue();

            System.out.println("Creating order for amount: " + amount);

            Order order = razorpayService.createOrder(amount);

            Map<String, Object> response = new HashMap<>();
            response.put("orderId", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("currency", order.get("currency"));
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error while creating Razorpay order: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public String verifyPayment(@RequestBody Map<String, String> data) throws Exception {
        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        String payload = orderId + "|" + paymentId;
        String expectedSignature = hmacSHA256(payload, razorpaySecret);

        if (expectedSignature.equals(signature)) {
            return "Payment verified successfully!";
        } else {
            return "Payment verification failed!";
        }
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes());
        return new String(org.apache.commons.codec.binary.Hex.encodeHex(hash));
    }

}
