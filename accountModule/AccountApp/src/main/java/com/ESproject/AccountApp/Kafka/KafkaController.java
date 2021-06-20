package com.ESproject.AccountApp.Kafka;

import com.ESproject.AccountApp.Request.AlarmTrueKafkaObject;
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
    private KafkaTemplate<String, ArrayList<AlarmTrueKafkaObject>> kafkaTemplate;

//    @Autowired
//    private KafkaTemplate<String, ArrayList<GraphicalCoinInformation>> kafkaTemplate2;


    public KafkaController() {}

    public void sendMessage(String TOPIC, ArrayList<AlarmTrueKafkaObject> request){
        kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(),request);
    }

//    public void sendMessage2(String TOPIC, ArrayList<GraphicalCoinInformation> graph){
//        kafkaTemplate2.send(TOPIC, UUID.randomUUID().toString(),graph);
//    }
}
