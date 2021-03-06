package com.crypto_tracker.retrieval.kafka;

import com.crypto_tracker.retrieval.coins.Coin;
import com.crypto_tracker.retrieval.coins.GraphicalCoinInformation;
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

    @Autowired
    private KafkaTemplate<String, ArrayList<GraphicalCoinInformation>> kafkaTemplate2;


    public KafkaController() {}

    public void sendMessage(String TOPIC, ArrayList<Coin> coins){
        kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(),coins);
    }

    public void sendMessage2(String TOPIC, ArrayList<GraphicalCoinInformation> graph){
        kafkaTemplate2.send(TOPIC, UUID.randomUUID().toString(),graph);
    }
}