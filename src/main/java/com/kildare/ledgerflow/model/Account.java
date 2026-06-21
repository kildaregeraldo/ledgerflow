package com.kildare.ledgerflow.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "accounts")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String holderName;
	
	@Column(nullable = false, precision = 19, scale =2)
	private BigDecimal balance;
	
	@Version
	private Long version;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@PrePersist
	
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	public  Account() {
		
	}
	
	public Account(String holderName, BigDecimal balance) {
        this.holderName = holderName;
        this.balance = balance;
    }

	public UUID getId() {
		return id;
	}


	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getVersion() {
		return version;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


}
