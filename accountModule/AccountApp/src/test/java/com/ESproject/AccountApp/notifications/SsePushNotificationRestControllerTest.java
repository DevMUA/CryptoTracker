package com.ESproject.AccountApp.notifications;

import com.ESproject.AccountApp.Account.Account;
import com.ESproject.AccountApp.Repository.AccountRepository;
import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties"
)
public class SsePushNotificationRestControllerTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @Test
//    public void doAddAlarm() {
//        Account account = new Account();
//        account.setFirstName("John");
//        account.setLastName("Wick");
//        account.setEmail("johnwick@gmail.com");
//        account.setPassword("password123");
//
//        Mockito.when(accountRepository.findAccountByEmail(account.getEmail())).thenReturn(Optional.of(account));
//
//        mvc.perform(MockMvcRequestBuilders.post("/addAlarm").contentType(MediaType.APPLICATION_JSON).content(JsonU))
//    }
//
//    @Test
//    public void doGetAlarms() {
//    }
}