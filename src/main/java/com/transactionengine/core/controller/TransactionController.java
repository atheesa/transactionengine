package com.transactionengine.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.transactionengine.core.config.SecurityConfig;
import com.transactionengine.core.dto.TransferRequest;
import com.transactionengine.core.model.Role;
import com.transactionengine.core.model.Transaction;
import com.transactionengine.core.model.User;
import com.transactionengine.core.repository.RoleRepository;
import com.transactionengine.core.repository.UserRepository;
import com.transactionengine.core.service.TransactionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final RoleRepository roleRepository;
    private final TransactionService transactionService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public TransactionController(TransactionService transactionService, RoleRepository roleRepository,UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.transactionService = transactionService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create Transaction

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction Transaction) {
        return transactionService.createTransaction(Transaction);
    }

    // Get Balance
    @GetMapping("{accountId}/balance")
    public BigDecimal getBalance(@PathVariable String accountId) {
        return transactionService.getBalance(accountId);
    }

    // Get History

    @GetMapping("{accountId}/history")
    public List<Transaction> getHistory(@PathVariable String accountId) {        
        return transactionService.getHistory(accountId);
    }
    

    // Transfer Money
    @PostMapping("/transfer")
    public String transferMoney(@Valid @RequestBody TransferRequest request, @RequestHeader("Idempotency-Key") String idempotencyKey ) {

        transactionService.transferMoney(idempotencyKey,request.getFromUser(), request.getToUser(), request.getAmount(),request.getCurrency());
        
        return "Transfer Successful";
    }
    
    // Create basic test accounts
    @PostMapping("/setup")
    public String setupTestInit() {

        Role roleUser = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        transactionService.createAccount("ACC-100", "Alice", "USD");
        transactionService.createAccount("ACC-200", "Bob", "USD");

       if(userRepository.findByUsername("alice").isEmpty()) {
            User alice = new User(
                "alice",
                passwordEncoder.encode("test_password_1"),
                Set.of(roleUser,roleAdmin)
            );
            userRepository.save(alice);
       }
        
 
       if(userRepository.findByUsername("bob").isEmpty()) {
            User bob = new User(
                "bob",
                passwordEncoder.encode("test_password_2"),
                Set.of(roleUser,roleAdmin)
            );
            userRepository.save(bob);
       }       
        
        
        

        return "System Initialized: Roles, Users, and Accounts created";
    }
    

}
