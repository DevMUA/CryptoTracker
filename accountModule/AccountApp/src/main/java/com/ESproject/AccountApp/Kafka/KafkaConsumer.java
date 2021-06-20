package com.ESproject.AccountApp.Kafka;

import com.ESproject.AccountApp.Request.AlarmResponseObject;
import com.ESproject.AccountApp.notifications.SsePushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaConsumer {

    @Autowired
    private KafkaController kafkaController;

    @Autowired
    private SsePushNotificationService ssePushNotificationService;


    @KafkaListener(id="listener4" , topics = "alarmResponse", groupId = "group_id13", containerFactory = "fooListener4")
    void listener(ArrayList<AlarmResponseObject> data) {
        ssePushNotificationService.notifyUsersOfAlarms(data.get(0));

    }
}