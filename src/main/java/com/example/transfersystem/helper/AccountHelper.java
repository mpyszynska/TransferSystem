package com.example.transfersystem.helper;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AccountHelper implements Serializable {

    private static final long serialVersionUID = -7442291702836545156L;

    private String accountNumber;

    private List<CurrencyAmountsHelper> currencyAmounts;




}
