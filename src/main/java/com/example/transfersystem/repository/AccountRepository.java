package com.example.transfersystem.repository;

import com.example.transfersystem.entity.Account;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface  AccountRepository extends CrudRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.accountNumber = :number")
    Account findAccountsByAccountNumber(@Param("number") String number);
}
