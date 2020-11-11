package com.app.bank;

import com.app.bank.model.Bank;
import com.app.bank.model.BankFactory;
import com.app.bank.repository.BankRepository;
import com.app.bank.service.BankService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class BankServiceJPATest {

    @Autowired
    private BankRepository bankRepository;

    private BankService bankService;

    @BeforeEach
    public void setUp(){
        bankService = new BankService();
        bankService.setRepository(bankRepository);
        bankRepository.deleteAll();
    }

    @Test
    public void testAddRegularBank(){
        Bank bank = BankFactory.createBank("International", "Barclays");
        bankService.addBank(bank);

        Assertions.assertNotNull(bankRepository.findByName("Barclays"));
    }

    @Test
    public void testAddAlreadyExistingBank(){
        Bank bank = BankFactory.createBank("International", "Barclays");
        bankRepository.save(bank);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.bankService.addBank(bank);
        });
    }

    @Test
    public void testAddNullBank(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.bankService.addBank(null);
        });
    }

    @Test
    public void testRemoveRegularBank(){
        Bank bank = BankFactory.createBank("International", "HSBC");
        bankRepository.save(bank);
        bankService.removeBank("HSBC");
        Assertions.assertNull(bankRepository.findByName("HSBC"));
    }

    @Test
    public void testRemoveNonExistingBank(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankService.removeBank("Transilvania");
        });
    }

    @Test
    public void testRemoveEmptyStringNameBank(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bankService.removeBank("");
        });
    }

    @Test
    public void testGetBanksWhenThereIsOnlyOne(){
        Bank bank = BankFactory.createBank("International", "ING");
        bankRepository.save(bank);

        List<Bank> actual = bankService.getBanks();
        List<Bank> expected = new ArrayList<Bank>();
        expected.add(bank);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetBanksWhenThereAreNone(){
        List<Bank> expected = new ArrayList<>();
        Assertions.assertEquals(expected, bankService.getBanks());
    }

    @Test
    public void testGetBanksWhenThereAreMultiple(){
        Bank bank1 = BankFactory.createBank("International", "ING");
        Bank bank2 = BankFactory.createBank("International", "HSBC");
        Bank bank3 = BankFactory.createBank("International", "Loyds");

        bankRepository.save(bank1);
        bankRepository.save(bank2);
        bankRepository.save(bank3);

        List<Bank> actual = bankService.getBanks();

        List<Bank> expected = new ArrayList<Bank>();
        expected.add(bank1);
        expected.add(bank2);
        expected.add(bank3);

        Assertions.assertEquals(expected, actual);
    }

}
