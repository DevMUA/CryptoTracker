package com.ESproject.AccountApp.notifications;

import com.ESproject.AccountApp.Account.Account;
import com.ESproject.AccountApp.Account.Alarm;
import com.ESproject.AccountApp.Account.FavouriteCoin;
import com.ESproject.AccountApp.Account.Notification;
import com.ESproject.AccountApp.Kafka.KafkaConsumer;
import com.ESproject.AccountApp.Kafka.KafkaController;
import com.ESproject.AccountApp.Repository.AccountRepository;
import com.ESproject.AccountApp.Repository.AlarmRepository;
import com.ESproject.AccountApp.Repository.NotificationRepository;
import com.ESproject.AccountApp.Request.AlarmQueryObject;
import com.ESproject.AccountApp.Request.AlarmResponseObject;
import com.ESproject.AccountApp.Request.AlarmTrueKafkaObject;
import com.ESproject.AccountApp.email.EmailService;
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

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private KafkaController kafkaController;

    @Autowired
    private EmailService emailService;

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

    public void addFavouriteCoin(Account account){
        Optional<Account> acc = accountRepository.findAccountByEmail(account.getEmail());
        if(acc.isPresent()){
            FavouriteCoin newFavouriteCoin = new FavouriteCoin();
            newFavouriteCoin.setCoinName(account.getFavouriteCoins().get(0).getCoinName());
            if(!acc.get().getFavouriteCoins().contains(newFavouriteCoin)){
                acc.get().getFavouriteCoins().add(newFavouriteCoin);
                accountRepository.save(acc.get());
            }
        }
    }

    public void deleteFavouriteCoin(int id, Account account){
        System.out.println("deleting favourite coin with id: " + id + "  and account : " + account.toString());
        Optional<Account> acc = accountRepository.findAccountByEmail(account.getEmail());
        if(acc.isPresent()){
            for(int i= 0; i < acc.get().getFavouriteCoins().size(); i++){
                if(id==acc.get().getFavouriteCoins().get(i).getAid())
                    acc.get().getFavouriteCoins().remove(i);
            }
        }
        accountRepository.save(acc.get());
    }

    public List<FavouriteCoin> getFavouriteCoins(Account account){
        Optional<Account> acc = accountRepository.findAccountByEmail(account.getEmail());
        if(acc.isPresent()){
            return acc.get().getFavouriteCoins();
        }
        return null;
    }

    @Scheduled(fixedRate = 10000)
    public void verifyIfAlarmsAreTrueRequests(){
        System.out.println("sending request");
        ArrayList<Account> accounts = new ArrayList<>(accountRepository.findAll());
        System.out.println("size " + accounts.size());
        if(accounts.size() == 0)
            return;
        for(int i = 0; i < accounts.size(); i++){
            AlarmTrueKafkaObject request = new AlarmTrueKafkaObject();
            request.setUserID(accounts.get(i).getId());
            ArrayList<AlarmQueryObject> alarms = new ArrayList<>();
            System.out.println("account alarm size " + accounts.get(i).getAlarms().size());
            if(accounts.get(i).getAlarms() != null) {
                for (int j = 0; j < accounts.get(i).getAlarms().size(); j++) {
                    AlarmQueryObject alarmQ = new AlarmQueryObject();
                    alarmQ.setAid(accounts.get(i).getAlarms().get(j).getAid());
                    alarmQ.setCoin(accounts.get(i).getAlarms().get(j).getCoin());
                    alarmQ.setCondition(accounts.get(i).getAlarms().get(j).getCondition());
                    alarmQ.setValue(accounts.get(i).getAlarms().get(j).getValue());
                    alarmQ.setEmail(accounts.get(i).getAlarms().get(j).isEmail());
                    alarmQ.setAlert(accounts.get(i).getAlarms().get(j).isAlert());

                    alarms.add(alarmQ);
                }
            }
            request.setAlarms(alarms);

            ArrayList<AlarmTrueKafkaObject> requests = new ArrayList<>();
            requests.add(request);
            kafkaController.sendMessage("alarmRequest", requests);
        }

    }

    public void notifyUsersOfAlarms(AlarmResponseObject object){
        if(object.getAlarmsThatAreTrue().size() > 0){
            Account newAccount = accountRepository.findAccountById(object.getUserID()).get();
            for(int i = 0; i < object.getAlarmsThatAreTrue().size(); i ++){
                Notification notification = new Notification();
                String newNotification = String.format("Coin %s is now %s of value %s",
                        newAccount.getAlarms().get(object.getAlarmsThatAreTrue().get(i)).getCoin(),
                        newAccount.getAlarms().get(object.getAlarmsThatAreTrue().get(i)).getCondition(),
                        String.valueOf(newAccount.getAlarms().get(object.getAlarmsThatAreTrue().get(i)).getValue())
                        );
                if(newAccount.getAlarms().get(object.getAlarmsThatAreTrue().get(i)).isEmail()){
                    emailService.send(newAccount.getEmail(),
                            buildEmail(newAccount.getFirstName(),
                                    newAccount.getAlarms().get(object.getAlarmsThatAreTrue().get(i)).getCoin(),
                            newAccount.getAlarms().get(object.getAlarmsThatAreTrue().get(i)).getCondition(),
                            String.valueOf(newAccount.getAlarms().get(object.getAlarmsThatAreTrue().get(i)).getValue()
                            )));
                }
                notification.setNotification(newNotification);
                System.out.println(notification.getNotification());
                newAccount.getNotifications().add(notification);
                //deleteAlarm(object.getAlarmsThatAreTrue().get(i),newAccount);
                //newAccount.getAlarms().remove(object.getAlarmsThatAreTrue().get(i));
                alarmRepository.delete(newAccount.getAlarms().get(i));
            }
            accountRepository.saveAndFlush(newAccount);
        }
    }

    private String buildEmail(String firstName, String coin, String condition, String value) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Your Alert has been triggered</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + firstName + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> " + coin + " is " + condition + " " + value + " </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">  </p> <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

//    @Transactional
//    public void createFake(Account account){
//        Optional<Account> acc = accountRepository.findAccountByEmail(account.getEmail());
//        if(acc.isPresent()){
//            acc.get().getNotifications().add(account.getNotifications().get(0));
//            accountRepository.save(acc.get());
//        }
//    }
}
