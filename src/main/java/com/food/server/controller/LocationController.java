package com.food.server.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.server.models.Order;
import com.food.server.repository.OrderRepository;

@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    private OrderRepository orderRepository;

    // GET /api/orders/{orderId}/location
    @GetMapping("/orders/{orderId}/updatelocation")
    public ResponseEntity<?> getLocation(@PathVariable String orderId) {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("Order not found");
        }

        Order order = optionalOrder.get();
        return ResponseEntity.ok(order);
    }
}
