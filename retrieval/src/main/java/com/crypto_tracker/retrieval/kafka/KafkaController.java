package com.crypto_tracker.retrieval.kafka;

import com.crypto_tracker.retrieval.coins.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@EnableKafka
@Component
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, ArrayList<Coin>> kafkaTemplate;

    public KafkaController() {}

    public void sendMessage(String TOPIC, ArrayList<Coin> coins){
        kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(),coins);
    }
}