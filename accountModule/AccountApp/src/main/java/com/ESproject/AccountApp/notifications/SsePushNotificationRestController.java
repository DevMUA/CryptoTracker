package com.ESproject.AccountApp.notifications;

import com.ESproject.AccountApp.Account.Alarm;
import com.ESproject.AccountApp.Repository.AccountRepository;
import com.ESproject.AccountApp.Repository.NotificationRepository;
import com.ESproject.AccountApp.Request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@CrossOrigin("*")
public class SsePushNotificationRestController {


    @Autowired
    SsePushNotificationService service;

    final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @CrossOrigin
    @GetMapping("/notification={email}")
    public ResponseEntity<SseEmitter> doNotify(@PathVariable String email) throws InterruptedException, IOException {
        final SseEmitter emitter = new SseEmitter();
        service.addEmitter(emitter);
        service.addEmail(email);
        service.doNotify();
        emitter.onCompletion(() -> service.removeEmitter(emitter));
        emitter.onTimeout(() -> service.removeEmitter(emitter));
        return new ResponseEntity<>(emitter, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/readnotification={email}")
    public void doNotifyRead(@PathVariable String email) throws InterruptedException, IOException {
        service.doNotifyRead(email);
    }

    @CrossOrigin
    @GetMapping("/addAlarm")
    public void doAddAlarm(@RequestBody UserRequest request) throws InterruptedException, IOException {
        service.addAlarm(request.getAccount());
    }

    @CrossOrigin
    @PostMapping("/getAlarms")
    public List<Alarm> doGetAlarms(@RequestBody UserRequest request) throws InterruptedException, IOException {
        return service.getAlarms(request.getAccount());
    }

    @CrossOrigin
    @PostMapping("/deleteAlarm={id}")
    public void doDeleteAlarm(@RequestBody UserRequest request,@PathVariable int id) throws InterruptedException, IOException {
        service.deleteAlarm(id,request.getAccount());
    }

    @CrossOrigin
    @GetMapping("/createfakeNotification={email}")
    public void doCreateFake(@RequestBody UserRequest request) throws InterruptedException, IOException {
        service.createFake(request.getAccount());
    }
}
