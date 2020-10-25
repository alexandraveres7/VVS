package com.app.bank;

import com.app.bank.model.Bank;
import com.app.bank.model.BankFactory;
import com.app.bank.repository.BankRepository;
import com.app.bank.service.BankService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private BankRepository bankRepositoryMock;

    private BankService bankService;

    @BeforeEach
    public void setUp(){
        bankService = new BankService();
        bankService.setRepository(bankRepositoryMock);
    }

    @Test
    public void testSearchEmptyStringName(){
        Assertions.assertNull(this.bankService.search(""));
    }

    @Test
    public void testSearchAlphaOnlyStringName(){
        Bank bank = BankFactory.createBank("International", "ING");
        when(bankRepositoryMock.findByName("ING")).thenReturn(bank);
        Assertions.assertEquals(bank, this.bankService.search("ING"));
    }

    @Test
    public void testSearchAlphaNumericalStringName(){
        Bank bank = BankFactory.createBank("International", "Allegro12");
        when(bankRepositoryMock.findByName("Allegro12")).thenReturn(bank);
        Assertions.assertEquals(bank,this.bankService.search("Allegro12"));
    }

    @Test
    public void testSearchSpecialCharactersStringName(){
        Bank bank = BankFactory.createBank("International", "!alpha@");
        when(bankRepositoryMock.findByName("!alpha@")).thenReturn(null);
        Assertions.assertEquals(bank,this.bankService.search("!alpha@"));
    }

    @Test
    public void testSearchBankNotPresentInRepo(){
        when(bankRepositoryMock.findByName("Transilvania")).thenThrow(new NoSuchElementException());
        Assertions.assertNull(this.bankService.search("Transilvania"));
    }

    @Test
    public void testSearchNull(){
        Exception exception = assertThrows(NullPointerException.class, () -> {
            this.bankService.search(null);
        });
    }
}
