package com.debzium_solidier.customer_solider.listener;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageListener {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger log = LogManager.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "postgres.public.customer", groupId = "customer-group")
    public void listen(ConsumerRecord<String, Object> record) {

//        log.info("\n Consumed record: {} \n", record);
//        log.info("Received Message: {}",record.topic());
//        log.info("TOPIC : {} Headers :  {}",record.topic(),record.headers());
//        log.info("Key: {}" , record.key());
//        log.info("\n\n\n\n Value: {} " , record.value());


        try {
            // Parse the value of the record as a JSON object
            JsonNode rootNode = objectMapper.readTree(String.valueOf(record.value()));

            // Access the payload node
            JsonNode payloadNode = rootNode.get("payload");
            log.info("Payload: {}", payloadNode);
            if (payloadNode == null) {
               log.info("No payload found in the message");
                return;
            }

            // Extract the "before" and "after" nodes
            JsonNode beforeNode = payloadNode.get("before");
            JsonNode afterNode = payloadNode.get("after");

            // Example: Extract individual fields from the "after" object
            if (afterNode != null) {
                int customerId = afterNode.get("customer_id").asInt();
                String customerName = afterNode.get("customer_name").asText();
                String city = afterNode.get("city").asText(null); // Handle nullability
                String country = afterNode.get("country").asText(null);

                log.info("Updated Customer Details:");
               log.info("ID: {}" , customerId);
               log.info("Name: {}" , customerName);
               log.info("City: {}" , city);
               log.info("Country: {}" , country);
            }

            // Example: Extract the operation type (op) from the payload
            String operation = payloadNode.get("op").asText();
           log.info("Operation: {}" , operation);

        } catch (Exception e) {
            log.error("Failed to process Kafka message: {}" , e.getMessage());
            e.printStackTrace();
        }



    }
}