package com.ESproject.AccountApp.Repository;

import com.ESproject.AccountApp.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query("SELECT a FROM Account a WHERE a.email = ?1")
    Optional<Account> findAccountByEmail(String email);
}
