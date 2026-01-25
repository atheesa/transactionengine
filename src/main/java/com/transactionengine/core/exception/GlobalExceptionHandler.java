package com.transactionengine.core.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.transactionengine.core.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    

    // CATCH RUNTIME ERRORS
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeExceptions(RuntimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"LOGIC ERROR",ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    // CATCH METHOD ARGUMENT EXCEPTION ERRORS

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errorsMap = new HashMap<String,String>();
        ex.getBindingResult().getFieldErrors().forEach(
            error -> errorsMap.put(error.getField(),error.getDefaultMessage()));
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"VALIDATION ERROR",errorsMap.toString());

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    
    // CATCH ALL OTHER EXCEPTIONS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"SYSTEM ERROR","CONTACT INTERNAL TEAM");
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
