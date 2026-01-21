package com.transactionengine.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.transactionengine.core.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    

    // CATCH RUNTIME ERRORS
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeExceptions(RuntimeException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"LOGIC ERROR",ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    
    // CATCH ALL OTHER EXCEPTIONS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"SYSTEM ERROR","CONTACT INTERNAL TEAM");
        return new ResponseEntity<ErrorResponse>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
