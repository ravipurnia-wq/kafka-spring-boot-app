package com.example.kafka.controller;

import com.example.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        kafkaProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent: " + message);
    }

    @PostMapping("/send/{key}")
    public ResponseEntity<String> sendMessageWithKey(
            @PathVariable String key,
            @RequestParam String message) {
        kafkaProducerService.sendMessage(key, message);
        return ResponseEntity.ok("Message sent with key " + key + ": " + message);
    }
}