package com.transactionengine.core.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferRequest {

    @NotBlank(message =  "Sender Id is required")
    private String fromUser;
    
    @NotBlank(message = "Receiver Id is required")
    private String toUser;
    
    @NotBlank(message = "Currency is required")
    private String currency;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Transfer amount must be postive ---- Amount needs to more than 0.01")
    private BigDecimal amount;
    
    
}
