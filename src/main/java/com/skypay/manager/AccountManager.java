package com.skypay.manager;

import com.skypay.entity.Transaction;
import com.skypay.service.AccountService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccountManager implements AccountService {

    private final int LIMIT = 100000;

    private int balance = 0;
    private List<Transaction> transactions =  new ArrayList<>();

    @Override
    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be non-negative.");
        }
        balance += amount;
        transactions.add(new Transaction(amount, LocalDate.now(), balance));
    }

    @Override
    public void withdraw(int amount) {
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }
        if (amount > this.LIMIT) {
            throw new IllegalArgumentException("Withdraw amount exceeds the allowed transaction limit.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw Amount must be non-negative.");
        }

        this.balance -= amount;
        transactions.add(new Transaction(amount * -1, LocalDate.now(), balance));
    }

    @Override
    public void printStatement() {
        System.out.println("Date       || Amount  || Balance");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            System.out.printf("%s || %-7d || %d%n",
                    transaction.getDate().format(formatter), transaction.getAmount(), transaction.getBalance());
        }
    }

}

