package com.ESproject.AccountApp.notifications;

import com.ESproject.AccountApp.Account.Account;
import com.ESproject.AccountApp.Account.Alarm;
import com.ESproject.AccountApp.Account.Notification;
import com.ESproject.AccountApp.Repository.AccountRepository;
import com.ESproject.AccountApp.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@EnableScheduling
public class SsePushNotificationService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    private static int index;
    private static String email;

    final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
    final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    final List<String> emails = new CopyOnWriteArrayList<>();

    public void addEmitter(final SseEmitter emitter) {
        emitters.add(emitter);
    }

    public void removeEmitter(final SseEmitter emitter) {
        emitters.remove(emitter);
    }

    public void addEmail(final String email){
        emails.add(email);
    }

    @Async
    @Scheduled(fixedRate = 5000)
    public void doNotify() throws IOException {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        List<Integer> deadEmails = new ArrayList<>();
        System.out.println("emmiters size : " + emitters.size());
        System.out.println("email size" + emails.size());
        index = 0;
        emitters.forEach(emitter -> {
            try {
                String emailvar = emails.get(index);
                System.out.println(emailvar);
                Optional<Account> account = accountRepository.findAccountByEmail(emailvar);
                if (!account.isPresent()) {
                  System.out.println("account not present in DB");
                   emitter.send(SseEmitter.event()
                          .data(""));
                }
                else if(account.get().getNotifications().size() != 0) {
                  for(int i = 0; i < account.get().getNotifications().size(); i++) {
                      System.out.println(account.get().getNotifications().get(i).getNotification());
                       emitter.send(SseEmitter.event()
                               .data(account.get().getNotifications().get(i).getNotification()));
                  }
                }
                {
                    emitter.send(SseEmitter.event()
                            .data(""));
                }
                index++;
            } catch (Exception e) {
                System.out.println("error");
                System.out.println(e);
                deadEmails.add(index);
                deadEmitters.add(emitter);
            }
        });
        System.out.println("dead emails " + deadEmails);
        emitters.removeAll(deadEmitters);
        System.out.println("emails before removal " + emails.size());
        for(int i = 0; i < deadEmails.size(); i++){
            emails.remove(emails.get(deadEmails.get(i)));
            System.out.println("removed element " + deadEmails.get(i));
        }
        //emails.removeAll(deadEmails);
        System.out.println("emails after removal " + emails.size());
    }

    @Transactional
    public void doNotifyRead(String email){
        Optional<Account> account = accountRepository.findAccountByEmail(email);
        account.get().getNotifications().clear();
        accountRepository.save(account.get());
    }

    @Transactional
    public void createFake(Account account){
        Optional<Account> acc = accountRepository.findAccountByEmail(account.getEmail());
        if(acc.isPresent()){
            acc.get().getNotifications().add(account.getNotifications().get(0));
            accountRepository.save(acc.get());
        }
    }

    public void addAlarm(Account account){
        Optional<Account> acc = accountRepository.findAccountByEmail(account.getEmail());
        if(acc.isPresent()){
            acc.get().getAlarms().add(account.getAlarms().get(0));
            accountRepository.save(acc.get());
        }
    }

    public void deleteAlarm(int id, Account account){
        System.out.println("deleting alarm with id: " + id + "  and account : " + account.toString());
        Optional<Account> acc = accountRepository.findAccountByEmail(account.getEmail());
        if(acc.isPresent()){
            for(int i= 0; i < acc.get().getAlarms().size(); i++){
                if(id==acc.get().getAlarms().get(i).getAid())
                    acc.get().getAlarms().remove(i);
            }
        }
        accountRepository.save(acc.get());
    }

    public List<Alarm> getAlarms(Account account){
        Optional<Account> acc = accountRepository.findAccountByEmail(account.getEmail());
        if(acc.isPresent()){
            return acc.get().getAlarms();
        }
        return null;
    }
}
