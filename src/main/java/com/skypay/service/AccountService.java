package com.skypay.service;

public interface AccountService {
    void deposit(int mount);
    void withdraw(int mount);
    void printStatement();
}

