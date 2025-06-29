package com.skypay;

import com.skypay.service.AccountService;
import lombok.Getter;

public class Account {

    private final AccountService accountService;

    @Getter
    private int balance = 0;

    public Account(AccountService accountService) {
        this.accountService = accountService;
    }

    public void deposit(int amount) {
        accountService.deposit(amount);
        this.balance += amount;
    }

    public void withdraw(int amount) {
        accountService.withdraw(amount);
        this.balance -= amount;
    }

    public void printStatement() {
        accountService.printStatement();
    }

}
