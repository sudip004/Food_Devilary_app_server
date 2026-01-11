package com.food.server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.server.models.DeliveryBoy;
import com.food.server.models.Order;
import com.food.server.repository.DeliveryBoyRepository;
import com.food.server.repository.OrderRepository;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class DeliveryController {

    @Autowired
    public DeliveryBoyRepository deliveryBoyRepository;
    @Autowired
    public OrderRepository orderRepository;

    // Get all Delivery Boys for using name assign
    @GetMapping("/getalldeliveryboys")
    public ResponseEntity<?> getAllDeleveryBoys() {
        List<DeliveryBoy> list = deliveryBoyRepository.findAll();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list); // 200 OK + data
        } else {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("No delivery boys found");
        }

    }

    @PatchMapping("/assignorder")
    public ResponseEntity<?> assignOrder(@RequestBody Map<String, String> map) {

        String deliveryBoyId = map.get("deliveryBoyId");
        String orderId = map.get("orderId");

        if (deliveryBoyId == null || orderId == null) {
            return ResponseEntity
                    .badRequest()
                    .body("deliveryBoyId and orderId are required");
        }

        // 1️⃣ Check delivery boy exists
        DeliveryBoy deliveryBoy = deliveryBoyRepository
                .findById(deliveryBoyId)
                .orElse(null);

        if (deliveryBoy == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Delivery boy not found");
        }

        // 2️⃣ Prevent duplicate assignment
        if (deliveryBoy.getOrders() != null &&
                deliveryBoy.getOrders().contains(orderId)) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Order already assigned");
        }

        // 3️⃣ Check order exists
        Order order = orderRepository
                .findById(orderId)
                .orElse(null);

        if (order == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Order not found");
        }

        // 4️⃣ Update delivery boy
        deliveryBoy.getOrders().add(orderId);
        deliveryBoyRepository.save(deliveryBoy);

        // 5️⃣ Update order
        order.setDeliveryBoyId(deliveryBoyId);
        order.setOrderStatus("Assigned");
        orderRepository.save(order);

        return ResponseEntity.ok("Order assigned successfully");
    }

    // Update Order -> DeleveryBoy Location

}
