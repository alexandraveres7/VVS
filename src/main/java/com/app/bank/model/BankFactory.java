package com.app.bank.model;

public class BankFactory {
    public static Bank createBank(String type, String name) {
        if (type.equals("International")) {
            return new InternationalBank(name, type);
        }
        return null;
    }
}
