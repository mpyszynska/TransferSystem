package com.example.transfersystem;

import com.example.transfersystem.entity.Account;
import com.example.transfersystem.entity.CurrencyAmounts;
import com.example.transfersystem.repository.AccountRepository;
import com.example.transfersystem.repository.CurrencyAmountsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@DataJpaTest
public class AccountEntityTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyAmountsRepository currencyAmountsRepository;


    @Test
    public void shouldCreateAccount() {

        Account acc = new Account();
        acc.setAccountNumber("000142006678");
        CurrencyAmounts currencyAmount = new CurrencyAmounts();
        currencyAmount.setAmount(BigDecimal.valueOf(90.0));
        currencyAmount.setCurrency("SEK");
        accountRepository.save(acc);
        currencyAmountsRepository.save(currencyAmount);

        Assertions.assertThat(acc.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldFindAccountByTargetNumber() {

        Account acc = new Account();
        acc.setAccountNumber("000142006678");
        CurrencyAmounts currencyAmount = new CurrencyAmounts();
        currencyAmount.setAmount(BigDecimal.valueOf(90.0));
        currencyAmount.setCurrency("SEK");
        List<CurrencyAmounts> list = new ArrayList<>();
        list.add(currencyAmount);
        acc.setCurrencyAmountsList(list);
        accountRepository.save(acc);
        currencyAmountsRepository.save(currencyAmount);
        Account account = accountRepository.findAccountsByAccountNumber("000142006678");

        org.junit.jupiter.api.Assertions.assertNotEquals(account, null);
    }

}
