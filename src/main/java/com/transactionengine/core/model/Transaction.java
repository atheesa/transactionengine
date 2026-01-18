package com.transactionengine.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table (name = "transactions")
@Data
public class Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;


    private String reference;

    private LocalDateTime timestamp;


    @PrePersist
    public void onCreate(){
        this.timestamp = LocalDateTime.now();
    }



}