package com.food.server.models;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "delivery_boys") // 👈 SAME collection name
public class DeliveryBoy {

    @Id
    private String id; // ObjectId mapped as String

    private String name;
    private String email;
    private String password;
    private String phone;
    private String vehicleNumber;
    private Boolean isActive;

    private Location currentLocation;

    private List<String> orders;
}

