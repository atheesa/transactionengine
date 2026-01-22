package com.transactionengine.core.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table(name="accounts")
public class Account {
    
    @Id
    private String id;

    @Column(nullable = false)
    private String username;

    
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    
    @Column(nullable = false)
    private String currency;

    @Version
    private Long version;
}
