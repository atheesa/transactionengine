package com.transactionengine.core.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {
    
    private String error;
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }




}
