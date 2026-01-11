package com.food.server.kafka.controller;



import org.springframework.web.bind.annotation.*;

import com.food.server.kafka.producer.KafkaProducerService;

@RestController
@RequestMapping("/api/kafka")
public class MessageController {

    private final KafkaProducerService producerService;

    public MessageController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/publish")
    public String sendMessage(@RequestParam("message") String message) {
        producerService.sendMessage(message);
        return "Message sent to Kafka: " + message;
    }
}
