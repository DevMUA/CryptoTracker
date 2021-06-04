package com.ESproject.AccountApp.Account;

import com.ESproject.AccountApp.registration.token.ConfirmationToken;
import com.ESproject.AccountApp.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppAccountService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppAccountRepository appAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appAccountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public String signUpUser(Account account) {
        boolean userExists = appAccountRepository.findByEmail(account.getEmail()).isPresent();

        if(userExists) {
            throw new IllegalStateException("email already taken");
        }
        System.out.println(account.getPassword());
        String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());

        account.setPassword(encodedPassword);

        appAccountRepository.save(account);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15), account);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public String loginUser(Account account) {
        Optional<Account> user = appAccountRepository.findByEmail(account.getEmail());

        if(!user.isPresent()) {
            throw new IllegalStateException("account doesn't exist");
        }
        if(!user.get().getEnabled())
            return "false";
        if(bCryptPasswordEncoder.matches(account.getPassword(),user.get().getPassword()))
            return "true";
        return "false";
    }

    public int enableAppUser(String email) {
        return appAccountRepository.enableAppUser(email);
    }
}
