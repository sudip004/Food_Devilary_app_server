package com.food.server.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.food.server.models.DeliveryBoy;

@Repository
public interface DeliveryBoyRepository extends MongoRepository<DeliveryBoy, String> {
    // Fetch active delivery boys
    List<DeliveryBoy> findByIsActiveTrue();

    // Fetch by email (login / lookup)
    DeliveryBoy findByEmail(String email);

    // Fetch by phone
    DeliveryBoy findByPhone(String phone);
}
