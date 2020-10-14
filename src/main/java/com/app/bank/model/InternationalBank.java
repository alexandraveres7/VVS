package com.app.bank.model;

import javax.persistence.Entity;

@Entity
public class InternationalBank extends Bank {

    public InternationalBank(){

    }

    public InternationalBank(String name, String type) {
        super(name, type);
    }
}