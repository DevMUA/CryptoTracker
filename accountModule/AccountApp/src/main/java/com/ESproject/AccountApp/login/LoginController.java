package com.ESproject.AccountApp.login;

import com.ESproject.AccountApp.registration.RegistrationRequest;
import com.ESproject.AccountApp.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public String login(@RequestBody LoginRequest request){
        return loginService.login(request);
    }

}
