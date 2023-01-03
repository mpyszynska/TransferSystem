package com.example.transfersystem.helper;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AccountsData implements Serializable {

    private static final long serialVersionUID = -5103051987534867424L;

    private List<AccountHelper> accounts;

}
