package com.app.bank.service;

import com.app.bank.model.Client;

public interface BankClientAccountManager extends ClientAccountManager {
    void deposit(String name, double sum, String currency);

    void withdraw(String name, double sum, String currency);
}
