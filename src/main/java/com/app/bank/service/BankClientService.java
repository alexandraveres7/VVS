package com.app.bank.service;

import com.app.bank.model.Client;
import com.app.bank.repository.BankClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankClientService implements BankClientAccountManager {

    @Autowired
    private BankClientRepository clients;

    private final InternalRevenueService internalRevenueService = InternalRevenueService.getInstance();

    public void registerClient(Client client){
        if (!client.getClientName().equals("") && !client.getCNP().equals("") && client.getCNP().length() == 13  )
            clients.save(client);
        else{
            throw new IllegalArgumentException();
        }
    }

    public void removeClientAccount(String CNP) {
        Client client = searchByCNP(CNP);
        try {
            if (client.getEURO() == 0.00 && client.getRON() == 0.00) {
                clients.delete(client);
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public BankClientRepository getClients() {
        return clients;
    }

    public Client searchByCNP(String CNP){
        List<Client> list = clients.findAll();
        for (Client client : list) {
            if (client.getCNP().equals(CNP)) {
                return client;
            }
        }
        return null;
    }

    public void startObserving(String CNP) {
        Client client = searchByCNP(CNP);
        try{
            internalRevenueService.registerClient(client);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void stopObserving(String CNP) {
        Client client = searchByCNP(CNP);
        try {
            internalRevenueService.removeClientAccount(client.getCNP());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deposit(String CNP, double sum, String currency) {
        Client client = searchByCNP(CNP);
            if (currency.equals("RON")) {
                depositRon(client, sum);
            } else if (currency.equals("EURO")) {
                depositEuro(client, sum);
            }
    }

    public void depositEuro(Client client, double sum) {
        double balance = client.getEURO() + sum;
        client.setEURO(balance);
        clients.save(client);
        this.internalRevenueService.update(client);
    }

    public void depositRon(Client client, double sum) {
        double updatedBalance = client.getRON() + sum;
        client.setRON(updatedBalance);
        clients.save(client);
        this.internalRevenueService.update(client);
    }

    @Override
    public void withdraw(String CNP, double sum, String currency) {
        Client client = searchByCNP(CNP);
            if (currency.equals("RON")) {
                withdrawRON(client, sum);
            } else if (currency.equals("EURO")) {
                withdrawEURO(client, sum);
            }
    }

    public void withdrawRON(Client client, double sum) {
        double updatedBalance = client.getRON() - sum;
        if (updatedBalance < 1000) {
            System.out.println("insufficient funds\n");
        } else {
            client.setRON(updatedBalance);
            clients.save(client);
            this.internalRevenueService.update(client);
        }
    }

    public void withdrawEURO(Client client, double sum) {
        double updatedBalance = client.getEURO() - sum;
        if (updatedBalance < 1000) {
            System.out.println("insufficient funds\n");
        } else {
            client.setEURO(updatedBalance);
            clients.save(client);
            this.internalRevenueService.update(client);
        }
    }

    /*public void checkBalance(String CNP) {
        Client client = searchByCNP(CNP);
        System.out.println(
                "Client account: " + client.getClientName() +
                        "\n\tRON: " + client.getRON() +
                        "\n\tEURO: " + client.getEURO()
        );
    }*/
}
