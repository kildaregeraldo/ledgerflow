package com.kildare.ledgerflow.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kildare.ledgerflow.dto.request.CreateAccountRequest;
import com.kildare.ledgerflow.dto.response.AccountResponse;
import com.kildare.ledgerflow.dto.response.StatementResponse;
import com.kildare.ledgerflow.dto.response.TransferResponse;
import com.kildare.ledgerflow.model.Account;
import com.kildare.ledgerflow.model.Transaction;
import com.kildare.ledgerflow.repository.TransactionRepository;
import com.kildare.ledgerflow.service.AccountService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountController(AccountService accountService,
                             TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

  
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest request) {

        Account account = accountService.createAccount(
                request.getHolderName(),
                request.getInitialBalance());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AccountResponse.from(account));
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable UUID id) {

        Account account = accountService.findById(id);
        return ResponseEntity.ok(AccountResponse.from(account));
    }

  
    @GetMapping("/{id}/statement")
    public ResponseEntity<StatementResponse> getStatement(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Account account = accountService.findById(id);

        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionRepository
                .findStatementByAccountId(id, pageable);

        StatementResponse response = StatementResponse.of(
                account.getId(),
                account.getBalance(),
                transactionPage.getContent()
                        .stream()
                        .map(TransferResponse::from)
                        .toList(),
                transactionPage.getNumber(),
                transactionPage.getTotalPages(),
                transactionPage.getTotalElements());

        return ResponseEntity.ok(response);
    }
}