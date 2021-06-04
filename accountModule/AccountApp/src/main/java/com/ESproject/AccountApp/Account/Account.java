package com.ESproject.AccountApp.Account;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@EqualsAndHashCode
public class Account implements UserDetails {
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean locked = false;
    private Boolean enabled = false;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    public Account(String firstName, String lastName, String email, String password, AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch=FetchType.EAGER,targetEntity = Alarm.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "user_alarm_fk", referencedColumnName = "id")
    private List<Alarm> alarms;

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch=FetchType.EAGER,targetEntity = Notification.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_notifications_fk", referencedColumnName = "id")
    private List<Notification> notifications;

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch=FetchType.EAGER,targetEntity = FavouriteCoin.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_favouritecoins_fk", referencedColumnName = "id")
    private List<FavouriteCoin> favouriteCoins;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFirstName(){ return firstName;}

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
