package com.ESproject.AccountApp.notifications;

import com.ESproject.AccountApp.Account.*;
import com.ESproject.AccountApp.AccountAppApplication;
import com.ESproject.AccountApp.Repository.AccountRepository;
import com.ESproject.AccountApp.Repository.NotificationRepository;
import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;


import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Filter;

import static org.junit.Assert.*;

//@SpringBootTest
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@DataJpaTest
//@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SsePushNotificationRestControllerTest.class,SsePushNotificationService.class, NotificationRepository.class})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class SsePushNotificationRestControllerTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private AppAccountService appAccountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private NotificationRepository notificationRepository;

    @Before
    public void setup() {
    }


    @Test
    @Disabled
    public void doAddAlarm() {
        Account account = new Account();
        account.setEmail("johnwick@gmail.com");
        account.setAppUserRole(AppUserRole.USER);
        account.setAlarms(new LinkedList<>());

        Alarm alarm = new Alarm();
        alarm.setCoin("Bitcoin");
        alarm.setCondition("Above");
        alarm.setValue(200);
        alarm.setEmail(true);
        alarm.setAlert(false);

        account.getAlarms().add(alarm);
        Mockito.when(accountRepository.findAccountByEmail(account.getEmail())).thenReturn(Optional.of(account));
        //accountRepository.saveAndFlush(account);

        try {
            //assertNotNull(account);
            mvc.perform(MockMvcRequestBuilders.post("/addAlarm").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"account\": {\"email\" : \"johnwick@gmail.com\" , \"alarms\" : [ {\"coin\" : \"Bitcoin\", \"condition\": \"Above\", \"value\" : \200, \"email\" : true , \"alert\": false }]     }    }"));

            Optional<Account> found = accountRepository.findAccountByEmail(account.getEmail());

            //assertNotNull(found);
            //assertNotNull(found.get());
            //assertNotNull(found.get().getAlarms());
            assertEquals(1,found.get().getAlarms().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddAlarm(){
        Account account = new Account();
        account.setEmail("johnwick@gmail.com");
        account.setAppUserRole(AppUserRole.USER);
        account.setAlarms(new LinkedList<>());

        Alarm alarm = new Alarm();
        alarm.setCoin("Bitcoin");
        alarm.setCondition("Above");
        alarm.setValue(200);
        alarm.setEmail(true);
        alarm.setAlert(false);

        account.getAlarms().add(alarm);

        AppAccountRepository appAccountRepository = Mockito.mock(AppAccountRepository.class);
        Mockito.when(appAccountRepository.save(account)).thenReturn(account);
        Mockito.when(appAccountRepository.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
        SsePushNotificationService ssePushNotificationService = Mockito.mock(SsePushNotificationService.class);
        Mockito.doNothing().when(ssePushNotificationService).addAlarm(account);

        try {
            mvc.perform(MockMvcRequestBuilders.post("/addAlarm").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"account\": {\"email\" : \"johnwick@gmail.com\" , \"alarms\" : [ {\"coin\" : \"Bitcoin\", \"condition\": \"Above\", \"value\" : \200, \"email\" : true , \"alert\": false }]     }    }"));
            Optional<Account> found = appAccountRepository.findByEmail(account.getEmail());
            assertEquals(true,found.isPresent());

            assertNotNull(found.get());

            assertNotNull(found.get().getAlarms());
            assertEquals(1,found.get().getAlarms().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void doGetAlarms() {
    }

    private byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}