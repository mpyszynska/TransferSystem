package com.example.transfersystem.helper;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyAmountsHelper implements Serializable {
    private static final long serialVersionUID = -3174968289064182567L;

    private String currency;

    private BigDecimal amount;
}
