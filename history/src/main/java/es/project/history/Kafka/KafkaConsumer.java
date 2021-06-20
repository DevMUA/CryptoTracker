package es.project.history.Kafka;

import es.project.history.CryptoInfo.Coin;
import es.project.history.CryptoInfo.CryptoInfoService;
import es.project.history.CryptoInfo.GraphicalCoinInformation;
import es.project.history.Requests.AlarmResponseObject;
import es.project.history.Requests.AlarmTrueKafkaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaConsumer {

    @Autowired
    private CryptoInfoService cryptoInfoService;

    @Autowired
    private KafkaController kafkaController;



    @KafkaListener(id="listener1" , topics = "coinsLiveUpdate", groupId = "group_id6", containerFactory = "fooListener")
    void listener(List<Coin> data) {

        ArrayList<Coin> coins = new ArrayList<>(data);
        cryptoInfoService.saveCoinList(coins);

    }

    @KafkaListener(id="listener2" ,topics = "historic", groupId = "group_id5", containerFactory = "fooListener2")
    void listener2(List<GraphicalCoinInformation> data) {

        ArrayList<GraphicalCoinInformation> graphInfo = new ArrayList<>(data);
        cryptoInfoService.saveGraphicalPoints(graphInfo);
    }

    @KafkaListener(id="listener3" ,topics = "alarmRequest", groupId = "group_id10", containerFactory = "fooListener3")
    void listener3(List<AlarmTrueKafkaObject> data) {
        System.out.println("received request for user " + data.get(0).getUserID());
        AlarmResponseObject response = cryptoInfoService.isAlarmTrue(data.get(0));
        System.out.println("sending " + response);
        kafkaController.sendMessage2("alarmResponse", response);
    }
}