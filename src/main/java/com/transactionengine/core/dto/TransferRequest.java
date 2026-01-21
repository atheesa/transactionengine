package com.transactionengine.core.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferRequest {

    private String fromUser;
    private String toUser;
    private String currency;
    private BigDecimal amount;
    
    
}
