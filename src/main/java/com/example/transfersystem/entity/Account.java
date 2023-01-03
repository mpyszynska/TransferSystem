package com.example.transfersystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="account")
@Getter
@Setter
public class Account implements Serializable {

    private static final long serialVersionUID = 1669466622623087449L;

    @GeneratedValue
    @Id
    private Long id;

    @Column(name="accountNumber")
    private String accountNumber;

    @OneToMany
    @JoinColumn(name = "account_id")
    private List<CurrencyAmounts> currencyAmountsList;

}
