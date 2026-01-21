package com.transactionengine.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.transactionengine.core.dto.TransferRequest;
import com.transactionengine.core.model.Transaction;
import com.transactionengine.core.service.TransactionService;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    // Create Transaction

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction Transaction) {
        return transactionService.createTransaction(Transaction);
    }

    // Get Balance
    @GetMapping("{accountId}/balance")
    public BigDecimal getBalance(@PathVariable String accountId) {
        return transactionService.getBalance(accountId);
    }

    // Get History

    @GetMapping("{accountId}/history")
    public List<Transaction> getHistory(@PathVariable String accountId) {        
        return transactionService.getHistory(accountId);
    }
    

    // Transfer Money
    @PostMapping("/transfer")
    public String transferMoney(@RequestBody TransferRequest request) {


        transactionService.transferMoney(request.getFromUser(), request.getToUser(), request.getCurrency(), request.getAmount());
        
        return "Transfer Successful";
    }
    


}
