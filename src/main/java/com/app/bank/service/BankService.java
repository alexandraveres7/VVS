package com.app.bank.service;

import com.app.bank.model.Bank;
import com.app.bank.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BankService {
    @Autowired
    BankRepository bankrepo;

    public void setRepository(BankRepository bankRepo){
        this.bankrepo = bankRepo;
    }

     public Bank search(String name){
         if (!name.equals("")){
             try {
                 return bankrepo.findByName(name);
             } catch (NoSuchElementException e) {
                 return null;
             }
         }
         else
             return null;
    }

    public void addBank(Bank bank){
        if (bank == null) {
            throw new IllegalArgumentException();
        }
        String bankName = bank.getName();
        if (bankrepo.findByName(bankName) != null) {
            throw new IllegalArgumentException();
        }
        bankrepo.save(bank);
    }

    public void removeBank(String name){
        Bank bank = search(name);
        if (bank == null){
            throw new IllegalArgumentException();
        }
        else {
            bankrepo.delete(bank);
        }
    }

    public List<Bank> getBanks(){
       return bankrepo.findAll();
    }
}
