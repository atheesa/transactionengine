package com.transactionengine.core.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transactionengine.core.model.Transaction;
import com.transactionengine.core.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    // SAVE TRANSACTION

    public Transaction createTransaction(Transaction transaction){
        
        transactionRepository.save(transaction);

        return transaction;
    }

    // GET BALANCE FOR ACCOUNT

    public BigDecimal getBalance(String accountId){
        
        BigDecimal accountBalance = transactionRepository.getBalance(accountId);
        
        if(accountBalance == null){
            return BigDecimal.ZERO;
        }
        else{
            
        return accountBalance;
        
        }
    }


    // GET TRANSACTION HISTORY

    public List<Transaction> getHistory(String accountId){
        List<Transaction> transactionHistory = transactionRepository.findByAccountId(accountId);
        return transactionHistory;
    }





}
