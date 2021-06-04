package com.ESproject.AccountApp.security.config;


import com.ESproject.AccountApp.Account.AppAccountService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppAccountService appAccountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/api/v*/registration/**").permitAll().and().authorizeRequests().antMatchers("/api/v*/login").permitAll().and().authorizeRequests().antMatchers("/notification*").permitAll().and().authorizeRequests().antMatchers("/readnotification*").permitAll().and().authorizeRequests().antMatchers("/createfakeNotification*").permitAll()
                .and().authorizeRequests().antMatchers("/addAlarm").permitAll().and().authorizeRequests().antMatchers("/getAlarms").permitAll()
                .and().authorizeRequests().antMatchers("/deleteAlarm=*").permitAll()
                .and().authorizeRequests().antMatchers("/getFavouriteCoins").permitAll()
                .and().authorizeRequests().antMatchers("/addFavouriteCoins").permitAll()
                .and().authorizeRequests().antMatchers("/deleteFavouriteCoin*").permitAll()
                .and().authorizeRequests().antMatchers("/getAccounts").permitAll().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appAccountService);
        return provider;
    }
}
