package com.ESproject.AccountApp.Account;

import com.ESproject.AccountApp.AccountAppApplication;
import com.ESproject.AccountApp.configuration.H2TestProfileJPAConfig;
import com.ESproject.AccountApp.registration.token.ConfirmationTokenRepository;
import com.ESproject.AccountApp.registration.token.ConfirmationTokenService;
import org.apache.zookeeper.Op;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

//@SpringBootTest(
//        classes = {
//                AccountAppApplication.class,
//                H2TestProfileJPAConfig.class
//        }
//)
//@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase
@DataJpaTest
public class AppAccountServiceTest {

    @MockBean
    private AppAccountRepository appAccountRepository;
    private AutoCloseable autoCloseable;
    private AppAccountService appAccountService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    public void setUp() throws Exception {
//        Account account = new Account();
//        account.setFirstName("John");
//        account.setLastName("Wick");
//        account.setEmail("johnwick@gmail.com");
//        account.setPassword("password123");
//
//        //Mockito.when(appAccountRepository.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
//        appAccountRepository.save(account);
        autoCloseable = MockitoAnnotations.openMocks(this);
        appAccountService = new AppAccountService(appAccountRepository,bCryptPasswordEncoder,confirmationTokenService);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    @Disabled
    public void testH2DB(){
        Account account = new Account();
        account.setFirstName("John");
        account.setLastName("Wick");
        account.setEmail("johnwick@gmail.com");
        account.setPassword("password123");

        //Mockito.when(appAccountRepository.findByEmail(account.getEmail())).thenReturn(Optional.of(account));
        appAccountRepository.save(account);

        Optional<Account> expected = appAccountRepository.findByEmail("johnwick@gmail.com");
        boolean isPresent = expected.isPresent();

        assertEquals(true,isPresent);
    }

    @Test
    public void loadUserByUsername() {
        Account account = new Account();
        account.setFirstName("John");
        account.setLastName("Wick");
        account.setEmail("johnwick@gmail.com");
        account.setPassword("password123");

        Mockito.when(appAccountRepository.findByEmail("johnwick@gmail.com")).thenReturn(Optional.of(account));
        appAccountService.loadUserByUsername("johnwick@gmail.com");
        verify(appAccountRepository).findByEmail("johnwick@gmail.com");
    }
}