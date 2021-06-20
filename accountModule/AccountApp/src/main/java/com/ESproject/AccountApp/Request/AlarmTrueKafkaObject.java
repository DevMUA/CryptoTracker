package com.ESproject.AccountApp.Request;

import com.ESproject.AccountApp.Account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlarmTrueKafkaObject {

    private int userID;

    private ArrayList<AlarmQueryObject> alarms;
}
