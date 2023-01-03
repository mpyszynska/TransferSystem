package com.example.transfersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="currencyAmounts")
@Getter
@Setter
public class CurrencyAmounts implements Serializable {

    private static final long serialVersionUID = 8816893666680281926L;

    @GeneratedValue
    @Id
    private Long id;

    @Column(name="currency")
    private String currency;

    @Column(name="amount")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

}
