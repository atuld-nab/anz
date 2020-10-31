package au.com.anz.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import au.com.anz.model.Account;
import au.com.anz.model.Transaction;
import au.com.anz.service.BankAccountService;



@WebMvcTest(BankAccountController.class)
public class BankAccountControllerTest {

    @MockBean
    private BankAccountService accountService;
    @Autowired
    private MockMvc            mvc;
    private Account            account;
    private List<Account>      accounts;
    private List<Transaction>  transactionList;

    @BeforeEach
    public void setup() {
        transactionList = new ArrayList<>();
        account = new Account();
        account.setAccountName("cba");
        final Transaction tx = new Transaction();
        final Transaction transaction = new Transaction();
        tx.setAccount(account);
        transaction.setAccount(account);
        transactionList.add(tx);
        transactionList.add(transaction);
        account.setTransactions(Arrays.asList(tx));
        accounts = new ArrayList<>();
        accounts.add(account);
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        given(accountService.getAllAccounts()).willReturn(accounts);
        mvc.perform(get("/accounts").accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetAllTransactionsReturnsTransactions() throws Exception {
        final Long accountNumber = 33L;
        given(accountService.findAccountTrans(accountNumber)).willReturn(transactionList);
        mvc.perform(get("/accounts/33/transactions").accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(2)));
    }
}
