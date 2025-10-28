package com.food.server.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "orders")

public class Order {
    @Id
    private String id;
    private String userId;
    private int amount;
    private String Address;
    private String paymentMethod;
    private String paymentStatus;
    private List<CartItem> items;
    private String orderStatus = "Order-Placed";
    private Date orderDate = new Date();

    // Delevari boy's location
    private Location restaurantLocation;
    private Location customerLocation;
    private Location deliveryBoyLocation;
}


