package com.kildare.ledgerflow.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.kildare.ledgerflow.model.enums.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")

public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private UUID sourceAccountId;
	
	@Column(nullable = false)
	private UUID targetAccountId;
	
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionStatus status;
	
	@Column(nullable = false, unique = true)
	private String idempotencyKey;
	
	@Column(nullable = false)
	private String actor;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	public Transaction() {
		
	}
		
	
	public Transaction(BigDecimal amount, String actor) {
		this.amount = amount;
		this.actor = actor;
		
	}

	public UUID getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(UUID sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public UUID getTargetAccountId() {
		return targetAccountId;
	}

	public void setTargetAccountId(UUID targetAccountId) {
		this.targetAccountId = targetAccountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public String getIdempotencyKey() {
		return idempotencyKey;
	}

	public void setIdempotencyKey(String idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public UUID getId() {
		return id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	
	
}
