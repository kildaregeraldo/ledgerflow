package com.kildare.ledgerflow.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.kildare.ledgerflow.model.Account;

public class AccountResponse {

	private UUID id;
	private String holderName;
	private BigDecimal balance;
	private LocalDateTime createdAt;
	
	public static AccountResponse from(Account account) {
		AccountResponse response = new AccountResponse();
		response.id = account.getId();
		response.holderName = account.getHolderName();
		response.balance = account.getBalance();
		response.createdAt = account.getCreatedAt();
		return response;
	}
	
	public UUID getId() {return id;}
	public String getHolderName() {return holderName;}
	public BigDecimal getBalance() {return balance;}
	public LocalDateTime getCreatedAt() {return createdAt;}
	
}
