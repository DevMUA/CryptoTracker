package es.project.history.Kafka;

import es.project.history.CryptoInfo.Coin;
import es.project.history.Requests.AlarmResponseObject;
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
    private KafkaTemplate<String, ArrayList<AlarmResponseObject>> kafkaTemplate2;


    public KafkaController() {}

    public void sendMessage(String TOPIC, ArrayList<Coin> coins){
        kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(),coins);
    }

    public void sendMessage2(String TOPIC, AlarmResponseObject response){
        ArrayList<AlarmResponseObject> responseBack = new ArrayList<>();
        responseBack.add(response);
        kafkaTemplate2.send(TOPIC, UUID.randomUUID().toString(),responseBack);
    }
}
