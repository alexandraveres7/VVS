package com.app.bank;

import com.app.bank.model.Bank;
import com.app.bank.model.BankFactory;
import com.app.bank.model.InternationalBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BankFactoryTest {

    @Test
    void testCreateRegularBank(){
        Bank actual_bank = BankFactory.createBank("International", "Deutsche Bank");
        InternationalBank expected_bank = new InternationalBank("Deutsche Bank", "International");
        assert actual_bank != null;
        Assertions.assertEquals(expected_bank.getName(), actual_bank.getName());
        Assertions.assertEquals(expected_bank.getType(), actual_bank.getType());
    }

    @Test
    void testCreateBankWithEmptyStringName(){
        Assertions.assertNull(BankFactory.createBank("International", ""));
    }

    @Test
    void testCreateBankWithNameLongerThan40(){
        Assertions.assertNull(BankFactory.createBank("International", "qwertyuiopasdfghjklzxcvbnmqwerfghjxcvbnmw"));
    }

    @Test
    void testCreateBankWithEmptyType(){
        Assertions.assertNull(BankFactory.createBank("", "London Bank"));
    }

    @Test
    void testCreateBankWithNameContainingSpecialCharacters(){
        Assertions.assertNull(BankFactory.createBank("International", "$#ING"));
    }

    @Test
    void testCreateBankWithEmptyTypeAndNameWithSpecialCharacters(){
        Assertions.assertNull(BankFactory.createBank("", "$#ING"));
    }

    @Test
    void testCreateBankWithEmptyTypeAndNameLongerThan40(){
        Assertions.assertNull(BankFactory.createBank("", "afejnfdiuetrtpjjfdnvfklenwjflaIWOEwertyuiopasdf"));
    }

    @Test
    void testCreateBankWithNameContainingNumbers(){
        Bank actual_bank = BankFactory.createBank("International", "RO123");
        InternationalBank expected_bank = new InternationalBank("RO123", "International");
        assert actual_bank != null;
        Assertions.assertEquals(expected_bank.getName(), actual_bank.getName());
        Assertions.assertEquals(expected_bank.getType(), actual_bank.getType());
    }

}
