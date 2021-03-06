package com.crypto_tracker.news.repository.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
public class KafkaController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public KafkaController() {}

    public void sendMessage(String TOPIC,String message){
        kafkaTemplate.send(TOPIC,message);
    }
}
