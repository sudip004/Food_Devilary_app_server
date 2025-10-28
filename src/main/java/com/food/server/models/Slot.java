package com.food.server.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "slots")

public class Slot {
    @Id
    private String id;
    private String userId;
    private String restaurantId;
    private String date;
    private String time;
    private String note;
    private String people;
}
