package com.example.kafka;

import com.example.kafka.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"sample-topic"})
public class KafkaApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSendMessageViaRestApi() {
        String testMessage = "Hello Kafka via REST API!";
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/kafka/send?message=" + testMessage,
            null,
            String.class
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Message sent: " + testMessage, response.getBody());
    }

    @Test
    public void testSendMessageWithKeyViaRestApi() {
        String testKey = "testkey123";
        String testMessage = "Hello Kafka with key!";
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/kafka/send/" + testKey + "?message=" + testMessage,
            null,
            String.class
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Message sent with key " + testKey + ": " + testMessage, response.getBody());
    }
}