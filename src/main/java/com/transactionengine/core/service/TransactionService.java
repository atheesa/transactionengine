package com.transactionengine.core.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transactionengine.core.model.Transaction;
import com.transactionengine.core.repository.TransactionRepository;

import jakarta.transaction.Transactional;

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


    // PROCESS TRANSFER
    @Transactional
    public void transferMoney(String fromUser, String toUser, String currency, BigDecimal amount){

        //Checking if amount is valid
        BigDecimal userBalance = getBalance(fromUser);
        if(userBalance.compareTo(amount) < 0){
            throw new RuntimeException("Insufficient Funds! Transfer Cancelled.");
        }

        // Debit Sender
        Transaction debit = new Transaction();
        debit.setAccountId(fromUser);
        debit.setAmount(amount.negate());
        debit.setCurrency(currency);
        debit.setReference("Transfer to ->" + toUser);
        transactionRepository.save(debit);


        // Credit Reciever
        Transaction credit = new Transaction();
        credit.setAccountId(toUser);
        credit.setAmount(amount);
        credit.setCurrency(currency);
        credit.setReference("Transfer from ->" + fromUser);
        transactionRepository.save(credit);

        System.out.println("Transfer Successful from " + fromUser + " to " + toUser);

    }




}
