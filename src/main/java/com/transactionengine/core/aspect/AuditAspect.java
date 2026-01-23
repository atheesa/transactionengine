package com.transactionengine.core.aspect;

import java.math.BigDecimal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.transactionengine.core.model.AuditLog;
import com.transactionengine.core.repository.AuditLogRepository;

@Aspect
@Component
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;

    public AuditAspect(AuditLogRepository auditLogRepository){
        this.auditLogRepository = auditLogRepository;
    }
    //String fromUser, String toUser, String currency, BigDecimal amount)
    @AfterReturning("execution(* com.transactionengine.core.service.TransactionService.transferMoney(..))")    
    public void logTransfer(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String fromUserId = (String) args[0];
        String toUser = (String) args[1];
        // String currency = (String) args[2];
        BigDecimal amount = (BigDecimal) args[3];
        
        AuditLog auditLog = new AuditLog();
        auditLog.setAction("TRANSFER_SUCCESS");
        auditLog.setUsername(fromUserId);
        auditLog.setDetails("Transferred " + amount + " to " + toUser);
        
        auditLogRepository.save(auditLog);
        
        System.out.println("<<<<------- AUDITED TRANSFER ------->>>>");


    }




}
