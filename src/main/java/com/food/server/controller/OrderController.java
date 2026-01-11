package com.food.server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.server.models.Location;
import com.food.server.models.Order;
import com.food.server.repository.OrderRepository;

@RestController
@RequestMapping("/api")
public class OrderController extends BaseClass {

    @Autowired
    private OrderRepository orderRepository;

    /*
     * =========================
     * CREATE ORDER
     * =========================
     */
    @PostMapping("/order-set")
    public ResponseEntity<String> setOrderItem(@RequestBody Order order) {
        if (order.getCustomerLocation() == null) {
            return ResponseEntity.badRequest().body("Customer location is required");
        }

        // Location lt = new Location();
        // lt.setLatitude(23.336211503603334);
        // lt.setLongitude(23.336211503603334);
        // order.setCustomerLocation(lt);
        orderRepository.save(order);
        return ResponseEntity.ok("Order saved successfully!");
    }

    /*
     * =========================
     * GET ALL ORDERS (ADMIN)
     * =========================
     */
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    /*
     * =========================
     * UPDATE ORDER STATUS
     * =========================
     */
    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable String orderId,
            @RequestBody java.util.Map<String, String> request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String newStatus = request.get("orderStatus");

        order.setOrderStatus(newStatus);
        orderRepository.save(order);

        return ResponseEntity.ok("Order status updated");
    }

    /*
     * =========================
     * UPDATE DELIVERY BOY LOCATION
     * =========================
     */
    @PatchMapping("/orders/{orderId}/location")
    public ResponseEntity<?> updateLocation(
            @PathVariable String orderId,
            @RequestBody Location newLocation) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDeliveryBoyLocation(newLocation);
        orderRepository.save(order);

        return ResponseEntity.ok("Location updated");
    }

    /*
     * =========================
     * GET DELIVERY BOY LOCATION
     * =========================
     */
    @GetMapping("/orders/{orderId}/location")
    public ResponseEntity<Location> getLocation(@PathVariable String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return ResponseEntity.ok(order.getDeliveryBoyLocation());
    }
}
