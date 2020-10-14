package com.app.bank.model;

public class ClientFactory {

    public static Client getClient(String type, String CNP, String name) {

        if (type.equals("Regular")){
            return new RegularClient(CNP, name);
        }
        return null;
    }
}