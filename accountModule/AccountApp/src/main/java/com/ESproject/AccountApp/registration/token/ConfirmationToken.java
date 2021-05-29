package com.ESproject.AccountApp.registration.token;

import com.ESproject.AccountApp.Account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;


    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name="account_id")
    private Account account;

    public ConfirmationToken(String token, LocalDateTime createAt, LocalDateTime expiredAt, Account account) {
        this.token = token;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.account = account;
    }
}
