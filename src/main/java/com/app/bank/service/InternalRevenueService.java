package com.app.bank.service;

import com.app.bank.model.Client;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InternalRevenueService implements ClientAccountManager {
    private static final InternalRevenueService irs = new InternalRevenueService();
    private final ArrayList<Client> observedClients = new ArrayList<>();

    public static InternalRevenueService getInstance()
    {
        return irs;
    }

    public ArrayList<Client> getObservedClients() {
        return observedClients;
    }

    @Override
    public void registerClient(Client client) {
        try {
                Client observedClient = (Client) client.clone();
                observedClients.add(observedClient);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void removeClientAccount(String CNP) {
      Client client = search(CNP);
      observedClients.remove(client);
    }

    public Client search(String CNP){
        for (Client client : observedClients) {
            if (client.getCNP().equals(CNP)) {
                return client; }
        }
        return null;
    }

    public void update(Client client) {
        double balanceRON = client.getRON();
        double balanceEURO = client.getEURO();
        Client notifiedClient = findNotifiedClient(client.getCNP());

        try{
            updateClientAccount(notifiedClient, balanceRON, balanceEURO);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void updateClientAccount(Client notifiedClient, double balanceRON, double balanceEURO) {
        try {
            if (notifiedClient.getRON() != balanceRON) {
                notifiedClient.setRON(balanceRON);
                notifyClient(notifiedClient, "RON");
            } else if (notifiedClient.getEURO() != balanceEURO) {
                notifiedClient.setEURO(balanceEURO);
                notifyClient(notifiedClient, "EURO"); }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public Client findNotifiedClient(String CNPClient) {
        for (Client client : observedClients) {
            if (client.getCNP().equals(CNPClient)) {
                return client;
            }
        }
        return null;
    }

    public void notifyClient(Client client, String account) {
        System.out.println(
                "Client " + client.getClientName() + " has updated his " + account + " account"
        );
    }
}
