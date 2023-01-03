package com.example.transfersystem.service;

import com.example.exercises.transfersystem.transfer_request_response.TransferRequest;
import com.example.transfersystem.entity.Account;
import com.example.transfersystem.helper.AccountHelper;
import com.example.transfersystem.helper.AccountsData;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AccountService {

    List<AccountHelper> getAllAccounts();

    void saveAccountsDb(AccountsData data);

    Account getAccountByTargetAccountNumber (String number);

    String actionsOnAccount (TransferRequest transferRequest) throws Exception;
}
