package com.skypay.manager;


import com.skypay.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    private Account account;

    @BeforeEach
    public void setup() {
        AccountManager realService = new AccountManager();
        account = new Account(realService);
    }

    @Test
    public void testDepositIncreasesBalance() {
        account.deposit(1000);
        assertEquals(1000, getBalance());
    }

    @Test
    public void testDepositNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.deposit(-1500));
        assertEquals("Deposit amount must be non-negative.", exception.getMessage());
    }

    @Test
    public void testWithdrawDecreasesBalance() {
        account.deposit(2000);
        account.withdraw(500);
        assertEquals(1500, getBalance());
    }

    @Test
    public void testWithdrawMoreThanBalanceThrows() {
        account.deposit(1000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(4000));
        assertEquals("Insufficient funds for withdrawal.", exception.getMessage());
    }

    @Test
    public void testWithdrawMoreThanLimitThrows() {
        account.deposit(10000000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(1000000));
        assertEquals("Withdraw amount exceeds the allowed transaction limit.", exception.getMessage());
    }

    @Test
    public void testWithdrawNegativeAmountThrows() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(-1000000));
        assertEquals("Withdraw Amount must be non-negative.", exception.getMessage());
    }

    @Test
    public void acceptanceTest_shouldPrintExpectedStatement_ignoringWhitespace() {
        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            AccountManager account = new AccountManager();

            account.deposit(1000);
            account.deposit(2000);
            account.withdraw(500);

            account.printStatement();

            LocalDate today = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = today.format(formatter);

            String expectedOutput = String.format(
                """
                Date || Amount || Balance
                %s || -500 || 2500
                %s || 2000 || 3000
                %s || 1000 || 1000
                """,
                    formattedDate, formattedDate, formattedDate);

            String actualOutput = outContent.toString();

            // Normalize by removing all whitespace
            String normalizedExpected = expectedOutput.replaceAll("\\s+", "");
            String normalizedActual = actualOutput.replaceAll("\\s+", "");

            assertEquals(normalizedExpected, normalizedActual);
        } finally {
            System.setOut(originalOut);
        }
    }

    public void integrationTest_multipleOperations() {
        account.deposit(1000);
        account.withdraw(200);
        account.deposit(500);
        assertEquals(1300, getBalance());
    }

    private int getBalance() {
        return account.getBalance();
    }
}
