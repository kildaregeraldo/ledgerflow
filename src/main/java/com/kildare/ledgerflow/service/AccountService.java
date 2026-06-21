package com.kildare.ledgerflow.service;

import java.math.BigDecimal;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kildare.ledgerflow.model.Account;
import com.kildare.ledgerflow.repository.AccountRepository;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	
	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public Account createAccount(String holderName, BigDecimal initialBalance) {
		Account account = new Account(holderName, initialBalance);
		return accountRepository.save(account);
	}
	
	public Account findById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
	
	public Account findByIdWithPessimisticLock(UUID id) {
		return accountRepository.findByIdWithPessimisticLock(id);
	}
}
