package com.ESproject.AccountApp.login;


import com.ESproject.AccountApp.Account.Account;
import com.ESproject.AccountApp.Account.AppAccountService;
import com.ESproject.AccountApp.Account.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppAccountService appAccountService;

    public String login(LoginRequest loginRequest){
        String response = appAccountService.loginUser(
                new Account("","" ,loginRequest.getEmail(),loginRequest.getPassword(), AppUserRole.USER)
        );

        return response;
    }

}
