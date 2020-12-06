package com.app.bank.model;

import javax.persistence.Entity;

@Entity
public class RegularClient extends Client {

    public RegularClient(){
    }

    public RegularClient(String CNP, String clientName) {
        super(CNP, clientName);
    }

    public String getTYPE() {
        String TYPE = "Regular";
        return TYPE; }
}
