package com.app.bank.model;

import javax.persistence.Entity;

@Entity
public class RegularClient extends Client {

    private final String TYPE = "Regular";

    public RegularClient(){
    }

    public RegularClient(String CNP, String clientName) {
        super(CNP, clientName);
    }

    public String getTYPE() { return TYPE; }
}
