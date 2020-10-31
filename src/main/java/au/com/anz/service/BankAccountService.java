package au.com.anz.service;

import java.util.List;

import au.com.anz.exception.ServiceException;
import au.com.anz.model.Account;
import au.com.anz.model.Transaction;

public interface BankAccountService {

    public List<Account> getAllAccounts() throws ServiceException;

    public List<Transaction> findAccountTrans(Long accountNumber) throws ServiceException;
}