package com.transactionengine.core.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.transactionengine.core.model.Transaction;



@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByAccountId(String accountId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.accountId = :accountId")
    BigDecimal getBalance(String accountId);

}
