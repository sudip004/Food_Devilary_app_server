package com.food.server.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")

public class User {
    
    @Id
    private String id;
   private String username;
   private String email;
   private String password;
   List<CartItem> cart = new ArrayList<>();
}
