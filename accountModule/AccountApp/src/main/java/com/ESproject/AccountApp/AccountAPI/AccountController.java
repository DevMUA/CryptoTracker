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

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            }
        }));
    }

    @PostMapping("/addAlarm")
    public void addAlarm(@RequestBody UserRequest request){
        Optional<Account> account = accountRepository.findAccountByEmail(request.getAccount().getEmail());
        if(account.isPresent()){
            account.get().getAlarms().add(request.getAccount().getAlarms().get(0));
            accountRepository.save(account.get());
        }
    }

    @PostMapping("/addNotification")
    public void addNotification(@RequestBody UserRequest request){
        Optional<Account> account = accountRepository.findAccountByEmail(request.getAccount().getEmail());
        if(account.isPresent()){
            account.get().getNotifications().add(request.getAccount().getNotifications().get(0));
            accountRepository.save(account.get());
        }
    }

    @PostMapping("/pushNotification={email}")
    public void pushNotification(@PathVariable String email){
//        Optional<Account> account = accountRepository.findAccountByEmail(email);
//        if(account.isPresent()){
//            account.get().getNotifications().add(account.get().getNotifications().get(0));
//            accountRepository.save(account.get());
//        }
        findAllNotifications(email);
    }

//    @PostMapping("/addAccount")
//    public void addAccount(@RequestBody UserRequest request){
//        Optional<Account> account = accountRepository.findAccountByEmail(request.getAccount().getEmail());
//        if(!account.isPresent())
//            accountRepository.save(request.getAccount());
//    }

//    @PostMapping("/login")
//    public boolean login(@RequestBody UserRequest request){
//        Optional<Account> account = accountRepository.findAccountByEmail(request.getAccount().getEmail());
//        if(account.isPresent()){
//            if(request.getAccount().getPassword().equals(account.get().getPassword())){
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }

    @GetMapping("/getAccounts")
    public List<Account> findAllAccounts(){
        return accountRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/listen={email}")
    public SseEmitter getEvents(@PathVariable String email) {
        System.out.println("new listen");
        SseEmitter emitter = new SseEmitter();
        emitters.put(email,emitter);
        emitter.onCompletion(() -> emitters.remove(email));
        return emitter;
    }

    @PostMapping("/notify")
    public void postMessage() {
        Set set= emitters.entrySet();
        Iterator itr=set.iterator();
        while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            System.out.println(entry.getKey());
            try {
                SseEmitter emitter = (SseEmitter)entry.getValue();
                emitter.send("hello");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @CrossOrigin
    @GetMapping("/getNotifications={email}")
    public SseEmitter findAllNotifications(@PathVariable String email){
        Optional<Account> account = accountRepository.findAccountByEmail(email);
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);


        if(account.get().getNotifications().size() == 0 )
            return sseEmitter;
        executor.execute(() -> {
                try {
                    sseEmitter.send(account.get().getNotifications().get(0).getNotification());
                    System.out.println(account.get().getNotifications());
                    sleep(2, sseEmitter);

                } catch (IOException e) {
                    e.printStackTrace();
                    sseEmitter.completeWithError(e);
                }

                account.get().getNotifications().clear();
                accountRepository.save(account.get());

            sseEmitter.complete();
        });

        return sseEmitter;
        //return account.get().getNotifications();
    }

    private void sleep(int seconds, SseEmitter sseEmitter) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            sseEmitter.completeWithError(e);
        }
    }

    @GetMapping("/getAlarms={email}")
    public List<Alarm> findAllAlarms(@PathVariable String email){
        Optional<Account> account = accountRepository.findAccountByEmail(email);
        return account.get().getAlarms();
    }
}
