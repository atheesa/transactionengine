package com.transactionengine.core.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transactionengine.core.model.Account;
import com.transactionengine.core.model.Transaction;
import com.transactionengine.core.repository.AccountRepository;
import com.transactionengine.core.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    // CREATE ACCOUNT
    public void createAccount(String id,String username,String currency){
        if(!accountRepository.existsById(id)){
            Account acc = new Account();
            acc.setId(id);
            acc.setUsername(username);
            acc.setCurrency(currency);
            acc.setBalance(BigDecimal.ZERO);
            accountRepository.save(acc);
        }
    }

    // SAVE TRANSACTION
    @Transactional
    public Transaction createTransaction(Transaction transaction){
        
        transactionRepository.save(transaction);

        Account acc = accountRepository.findById(transaction.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found:" + transaction.getAccountId()));
        acc.setBalance(acc.getBalance().add(transaction.getAmount()));

        accountRepository.save(acc);
        
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

        // Fetch accounts
        Account sender = accountRepository.findById(fromUser).orElseThrow(() -> new RuntimeException("From Account Not Found"));
        Account reciever = accountRepository.findById(toUser).orElseThrow(() -> new RuntimeException("To Account Not Found"));

        // Getting account balances
        BigDecimal senderAccountBalance = sender.getBalance();
        BigDecimal recieverAccountBalance = reciever.getBalance();

        
        //Checking if amount is valid
        if(senderAccountBalance.compareTo(amount) < 0){
            throw new RuntimeException("Insufficient Funds! Transfer Cancelled.");
        }

        // Update account balances
        sender.setBalance(senderAccountBalance.subtract(amount));
        reciever.setBalance(recieverAccountBalance.add(amount));

        // Save account changes. Optimistic Lock Check Occurs HERE
        accountRepository.save(sender);
        accountRepository.save(reciever);


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
