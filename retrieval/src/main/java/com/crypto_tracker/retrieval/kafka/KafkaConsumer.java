package com.crypto_tracker.retrieval.kafka;

import com.crypto_tracker.retrieval.coins.Coin;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {


//    @KafkaListener(topics= "coinsLiveUpdate",groupId = "group_id")
//    void consume(List<Coin> message){
//        System.out.println("MESSAGE RECEIVED "+ message.get(0).getDescription());
//    }

    @KafkaListener(topics = "coinsLiveUpdate", groupId = "group_id4", containerFactory = "fooListener")
    void listener(List<Coin> data) {
        System.out.println("RECEIVED MESSAGE " + data.get(1).getDescription().getEn());
        //data.forEach(o-> System.out.println(o.getDescription()));
    }
}