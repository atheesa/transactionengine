package com.transactionengine.core.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "idempotency_keys")
@Data
@Entity
@NoArgsConstructor
public class IdempotencyKey {

    @Id
    private String key;

    @Column(nullable = false)
    private int status;

    private LocalDateTime timestamp;

    public IdempotencyKey(String key,int status){
        this.timestamp = LocalDateTime.now();
        this.key = key;
        this.status = status;

    }



}