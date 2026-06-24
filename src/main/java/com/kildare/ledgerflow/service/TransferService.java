package com.kildare.ledgerflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kildare.ledgerflow.repository.TransactionRepository;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import com.kildare.ledgerflow.exception.DuplicateTransactionException;
import com.kildare.ledgerflow.exception.InsufficientBalanceException;
import com.kildare.ledgerflow.model.Account;
import com.kildare.ledgerflow.model.Transaction;
import com.kildare.ledgerflow.model.enums.TransactionStatus;

import jakarta.persistence.OptimisticLockException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferService {

	private final AccountService accountService;
	private final TransactionRepository transactionRepository;
	private final AuditService auditService;
	
	@Autowired
	public TransferService(AccountService accountService, TransactionRepository transactionRepository, AuditService auditService) {
		this.accountService = accountService;
		this.transactionRepository = transactionRepository;
		this.auditService = auditService;
	}
	
	private Transaction processTransfer(UUID sourceAccountId, UUID targetAccountId, BigDecimal amount, String idempotencyKey, String actor) {
		if(amount ==null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Valor da trasnfêrencia deve ser maior que zero");
		}
		if(sourceAccountId.equals(targetAccountId)) {
			throw new IllegalArgumentException("Conta de origem e destino não podem ser iguais");
		}
		
		Account source = accountService.findById(sourceAccountId);
		Account target = accountService.findById(targetAccountId);
		
		if(source.getBalance().compareTo(amount) <0) {
			auditService.log(null,"TRANSFER_FAILED_INSUFFICIENT_BALANCE",
					         "Saldo insuficiente na conta" + sourceAccountId +
					         ": disponivel R$" + source.getBalance()+
					         ", solicitado R$" + amount);
			throw new InsufficientBalanceException(sourceAccountId, amount, source.getBalance());
		}
		source.setBalance(source.getBalance().subtract(amount));
		target.setBalance(target.getBalance().add(amount));
		Transaction transaction = new Transaction(amount, actor);
		transaction.setSourceAccountId(sourceAccountId);
		transaction.setTargetAccountId(targetAccountId);
		transaction.setIdempotencyKey(idempotencyKey);
		transaction.setStatus(TransactionStatus.COMPLETED);
		transactionRepository.save(transaction);
		
		auditService.log(transaction.getId(), "TRANSFER_SUCCESS",
				          "Transferencia de R$" + amount +
				          " de " + sourceAccountId + " para " + targetAccountId);
		
		return transaction;
	}
	
	@Recover
	@Transactional
	public Transaction recoverTransfer(OptimisticLockException ex,UUID sourceAccountId,UUID targetAccountId, BigDecimal amount, String idempotencyKey, String actor) {
			Transaction transaction = new Transaction(amount, actor);
			transaction.setSourceAccountId(sourceAccountId);
			transaction.setTargetAccountId(targetAccountId);
			transaction.setIdempotencyKey(idempotencyKey + "_FAILED");
			transaction.setStatus(TransactionStatus.FAILED);
			transactionRepository.save(transaction);
			
			auditService.log(transaction.getId(),
					         "TRANSFER_FAILED_CONCURRENCY",
					          "Falha após 3 tentativas de retry por conflito de concorrência. " +
					         "Conta origem: " + sourceAccountId);
			
			throw new DuplicateTransactionException(idempotencyKey);
	}
	
	@Retryable(
	retryFor = OptimisticLockException.class,
	maxAttempts = 3,
	backoff = @Backoff(delay =100, multiplier =2)
	)
	@Transactional
	public Transaction transfer(UUID sourceAccountId, UUID targetAccountId, BigDecimal amount, String idempotencyKey, String actor) {
		return transactionRepository.findByIdempotencyKey(idempotencyKey)
				.orElseGet(() -> processTransfer(sourceAccountId,targetAccountId,amount, idempotencyKey,actor));
	}
	
}
