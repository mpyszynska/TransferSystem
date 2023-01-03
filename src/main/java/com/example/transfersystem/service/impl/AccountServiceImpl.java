package com.example.transfersystem.service.impl;

import com.example.exercises.transfersystem.transfer_request_response.OutcomeType;
import com.example.exercises.transfersystem.transfer_request_response.TransferRequest;
import com.example.transfersystem.entity.CurrencyAmounts;
import com.example.transfersystem.helper.AccountHelper;
import com.example.transfersystem.helper.AccountsData;
import com.example.transfersystem.helper.CurrencyAmountsHelper;
import com.example.transfersystem.repository.AccountRepository;
import com.example.transfersystem.entity.Account;
import com.example.transfersystem.repository.CurrencyAmountsRepository;
import com.example.transfersystem.service.AccountService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.exercises.transfersystem.transfer_request_response.ActionType.CREDIT;
import static com.example.exercises.transfersystem.transfer_request_response.ActionType.DEBIT;
import static com.example.transfersystem.service.impl.ApplicationServiceImpl.PATH;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyAmountsRepository currencyAmountsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AccountHelper> getAllAccounts() {
        List<Account> result = new ArrayList<>();
        accountRepository.findAll().forEach(result::add);
        List<AccountHelper> resultList = result
                .stream()
                .map(user -> modelMapper.map(user, AccountHelper.class))
                .collect(Collectors.toList());

        return resultList;
    }

    @Override
    public Account getAccountByTargetAccountNumber(String number) {
        return accountRepository.findAccountsByAccountNumber(number);
    }

    private void updateJsonFile(String path, CurrencyAmounts currency, Account account) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        AccountsData data = gson.fromJson(new FileReader(path), AccountsData.class);
        CurrencyAmountsHelper currencyAmountFromJson = data.getAccounts().stream().filter(a -> a.getAccountNumber().equals(account.getAccountNumber())).flatMap(e -> e.getCurrencyAmounts().stream().filter(b -> b.getCurrency().equals(currency.getCurrency()))).findAny().orElse(null);
        currencyAmountFromJson.setAmount(currency.getAmount());
        Writer writer = new FileWriter(path);
        gson.toJson(data, writer);
        writer.flush();
        writer.close();
    }

    @Override
    public String actionsOnAccount (TransferRequest request) throws Exception {
        String outcome=OutcomeType.REJECT.value();
        Account account = getAccountByTargetAccountNumber(request.getTargetAccountNumber());
        if (account != null) {
            CurrencyAmounts currency = account.getCurrencyAmountsList().stream()
                    .filter(a -> a.getCurrency().equals(request.getCurrency()))
                    .findAny()
                    .orElse(null);
            if (currency != null) {
                if(CREDIT.equals(request.getAction())) {
                    currency.setAmount((request.getQuantity().add(currency.getAmount())));
                    currencyAmountsRepository.save(currency);
                    outcome = OutcomeType.ACCEPT.value();
                    updateJsonFile(PATH, currency,account);
                } else if (DEBIT.equals(request.getAction())) {
                    if (currency.getAmount().compareTo(request.getQuantity()) >= 0) {
                        currency.setAmount(currency.getAmount().subtract(request.getQuantity()));
                        currencyAmountsRepository.save(currency);
                        outcome = OutcomeType.ACCEPT.value();
                        updateJsonFile(PATH, currency,account);
                    }
                } else {
                    throw new Exception("Your action is not supported!");
                }
            }
        }
        return outcome;
    }

    @Override
    public void saveAccountsDb(AccountsData data) {

        for(AccountHelper accountHelper : data.getAccounts()) {
            Account accountEntity = new Account();
            accountEntity.setAccountNumber(accountHelper.getAccountNumber());
            accountRepository.save(accountEntity);

            for(CurrencyAmountsHelper currencyAmountsHelper : accountHelper.getCurrencyAmounts()) {
                CurrencyAmounts currencyAmountsEntity = new CurrencyAmounts();
                currencyAmountsEntity.setCurrency(currencyAmountsHelper.getCurrency());
                currencyAmountsEntity.setAmount(currencyAmountsHelper.getAmount());
                currencyAmountsEntity.setAccount(accountEntity);
                currencyAmountsRepository.save(currencyAmountsEntity);
            }
        }

    }
}
