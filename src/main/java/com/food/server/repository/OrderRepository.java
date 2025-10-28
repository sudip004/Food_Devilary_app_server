package com.food.server.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.food.server.models.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
        
}