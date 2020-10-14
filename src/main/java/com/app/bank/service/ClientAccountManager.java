package com.app.bank.service;

import com.app.bank.model.Client;

public interface ClientAccountManager {
    void registerClient(Client client);

    void removeClientAccount(String CNP);
}
