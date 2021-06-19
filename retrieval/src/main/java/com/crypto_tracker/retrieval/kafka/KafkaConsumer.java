package com.crypto_tracker.retrieval.kafka;

import com.crypto_tracker.retrieval.coins.Coin;
import com.crypto_tracker.retrieval.coins.GraphicalCoinInformation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {


//    @KafkaListener(topics= "coinsLiveUpdate",groupId = "group_id")
//    void consume(List<Coin> message){
//        System.out.println("MESSAGE RECEIVED "+ message.get(0).getDescription());
//    }

    @KafkaListener(id="listener1" , topics = "coinsLiveUpdate", groupId = "group_id6", containerFactory = "fooListener")
    void listener(List<Coin> data) {
        System.out.println("RECEIVED MESSAGE " + data.get(1).getDescription().getEn());
        //data.forEach(o-> System.out.println(o.getDescription()));
    }

    @KafkaListener(id="listener2" ,topics = "historic", groupId = "group_id5", containerFactory = "fooListener2")
    void listener2(List<GraphicalCoinInformation> data) {
        System.out.println("RECEIVED MESSAGE " + data.get(0).getId());
        //System.out.println("ONE POINT IS " + data.get(0).getPlotPoints().get(0).getTime());
        //data.forEach(o-> System.out.println(o.getDescription()));
    }
}