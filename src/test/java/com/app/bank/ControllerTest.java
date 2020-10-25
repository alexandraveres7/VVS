package com.app.bank;

import com.app.bank.model.Bank;
import com.app.bank.model.BankFactory;
import com.app.bank.repository.BankRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get Home Page -> Page responds with 200 and contains expected text")
    public void testGetHomePage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Hello !")));
    }

    @Test
    @DisplayName("Get banks list page when there are no banks available")
    public void testListBanksWhenThereAreNoBanks() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/listBanks")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("listbanks"))
                .andExpect(content().string(containsString("No Banks Available")));

    }
}
