package com.ESproject.AccountApp.Account;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest(
)
@RunWith(SpringRunner.class)
public class AppAccountServiceTest {


    @Autowired
    private AppAccountService appAccountService;

    @MockBean
    private AppAccountRepository appAccountRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        Account account = new Account();
        account.setFirstName("John");
        account.setLastName("Wick");
        account.setEmail("johnwick@gmail.com");
        account.setPassword("password123");

        Mockito.when(appAccountRepository.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
    }

    @org.junit.Test
    public void loadUserByUsername() {
        String email = "johnwick@gmail.com";
        UserDetails found = appAccountService.loadUserByUsername(email);

        assertEquals("johnwick@gmail.com",found.getUsername());
    }
}