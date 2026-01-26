package com.transactionengine.core.service;

import org.springframework.stereotype.Service;

import com.transactionengine.core.model.IdempotencyKey;
import com.transactionengine.core.repository.IdempotencyKeyRepository;

import jakarta.transaction.Transactional;

@Service
public class IdempotencyKeyService {

    private final IdempotencyKeyRepository idempotencyKeyRepository;

    public IdempotencyKeyService( IdempotencyKeyRepository idempotencyKeyRepository){
        this.idempotencyKeyRepository = idempotencyKeyRepository;
    }

    public boolean isDuplicate(String key){
        return idempotencyKeyRepository.existsById(key);
    }

    @Transactional 
    public void saveKey(String key) {
        idempotencyKeyRepository.save(new IdempotencyKey(key, 200));
    }
}
