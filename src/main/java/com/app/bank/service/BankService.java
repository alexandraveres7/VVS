package com.app.bank.service;

import com.app.bank.model.Bank;
import com.app.bank.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    @Autowired
    BankRepository bankrepo;

    public Bank search(String name){
        List<Bank> banks = bankrepo.findAll();
        for (Bank bank : banks){
            if(bank.getName().equals(name)){
                return bank;
            }
        }
        return null;
    }

    public void addBank(Bank bank){
        bankrepo.save(bank);
    }

    public void removeBank(String name){
        Bank bank = search(name);
        bankrepo.delete(bank);
    }

    public List<Bank> getBanks(){
       return bankrepo.findAll();
    }
}
