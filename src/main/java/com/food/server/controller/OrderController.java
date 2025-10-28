package com.food.server.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.server.models.Location;
import com.food.server.models.Order;
import com.food.server.repository.OrderRepository;


@RestController
@RequestMapping("/api")


public class OrderController extends BaseClass {

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/order-set")
    public String setOrderItem(@RequestBody Order order) {
       
        orderRepository.save(order);
        System.out.println(" Order saved: " + order.getItems());
        return "Order saved successfully!";
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    // Delevery boy location update
     @PatchMapping("/{orderId}/location")
    public ResponseEntity<?> updateLocation(@PathVariable String orderId, @RequestBody Location newLocation) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setDeliveryBoyLocation(newLocation);
        orderRepository.save(order);
        return ResponseEntity.ok("Location updated");
    }

    @GetMapping("/{orderId}/location")
    public ResponseEntity<Location> getLocation(@PathVariable String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return ResponseEntity.ok(order.getDeliveryBoyLocation());
    }
}

