package com.ESproject.AccountApp.notifications;

import com.ESproject.AccountApp.Account.Account;
import com.ESproject.AccountApp.Account.Alarm;
import com.ESproject.AccountApp.Account.AppUserRole;
import com.ESproject.AccountApp.AccountAppApplication;
import com.ESproject.AccountApp.Repository.AccountRepository;
import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


import java.util.LinkedList;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AccountAppApplication.class
)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class SsePushNotificationRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
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
        //Mockito.when(accountRepository.findAccountByEmail(account.getEmail())).thenReturn(Optional.of(account));
        accountRepository.saveAndFlush(account);

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
    public void doGetAlarms() {
    }

    private byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}