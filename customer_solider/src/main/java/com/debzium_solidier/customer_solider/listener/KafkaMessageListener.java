package com.debzium_solidier.customer_solider.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageListener {

    private static final Logger log = LogManager.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "postgres.public.customer", groupId = "customer-group")
    public void listen(ConsumerRecord<String, Object> record) {
        log.info("\n Consumed record: {} \n", record);
        log.info("Received Message: {}",record.topic());
        log.info("TOPIC : {} Headers :  {}",record.topic(),record.headers());
        log.info("Key: {}" , record.key());
        log.info("Value: {} " , record.value());

    }
}