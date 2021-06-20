package com.ESproject.AccountApp.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Alarm {

    @Id
    @GeneratedValue
    private int aid;
    private String coin;
    private String condition;
    private long value;
    private boolean email;
    private boolean alert;
}
