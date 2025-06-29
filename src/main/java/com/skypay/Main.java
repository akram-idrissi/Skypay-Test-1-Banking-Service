package com.skypay;


import com.skypay.manager.AccountManager;

public class Main {
    public static void main(String[] args) {

        Account account = new Account(new AccountManager());

        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);
        account.printStatement();
    }
}