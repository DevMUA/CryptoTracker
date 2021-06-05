package com.ESproject.AccountApp.AccountAPI;


import com.ESproject.AccountApp.Account.Account;
import com.ESproject.AccountApp.Account.Alarm;
import com.ESproject.AccountApp.Account.Notification;
import com.ESproject.AccountApp.Repository.AlarmRepository;
import com.ESproject.AccountApp.Repository.NotificationRepository;
import com.ESproject.AccountApp.Repository.AccountRepository;
import com.ESproject.AccountApp.Request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@CrossOrigin("*")
@RestController
public class AccountController {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final Map<String,SseEmitter> emitters = new HashMap<String,SseEmitter>();

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private NotificationRepository notificationRepository;


}
