package com.example.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "sample-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info("Sending message: {}", message);
        kafkaTemplate.send(TOPIC, message);
    }

    public void sendMessage(String key, String message) {
        logger.info("Sending message with key {}: {}", key, message);
        kafkaTemplate.send(TOPIC, key, message);
    }
}