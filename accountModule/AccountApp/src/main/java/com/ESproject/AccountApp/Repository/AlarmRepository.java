package com.ESproject.AccountApp.Repository;

import com.ESproject.AccountApp.Account.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm,Integer> {
}
