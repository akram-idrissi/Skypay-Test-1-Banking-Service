package com.skypay.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private int amount;
    private LocalDate date;
    private int balance;
}
