package com.app.bank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID ;

    private String CNP;
    private String clientName;
    private double RON, EURO;

    public Client() {
    }

    protected Client(String CNP, String clientName) {
        this.clientName = clientName;
        this.CNP = CNP;
    }

    public String getClientName() {
        return clientName;
    }

    public String getCNP() {
        return CNP;
    }

    public double getEURO() {
        return EURO;
    }

    public double getRON() {
        return RON;
    }

    public Long getID() { return ID; }

    public void setRON(double RON) {
        this.RON = RON;
    }

    public void setEURO(double EURO) {
        this.EURO = EURO;
    }

    public void setCNP(String CNP) { this.CNP = CNP; }

    public void setClientName(String clientName) { this.clientName = clientName; }

    public Object clone() throws CloneNotSupportedException { return super.clone(); }
}
