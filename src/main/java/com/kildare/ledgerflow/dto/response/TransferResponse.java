package com.kildare.ledgerflow.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.kildare.ledgerflow.model.Transaction;

public class TransferResponse {

	private UUID id;
	private UUID sourceAccountId;
	private UUID targetAccountId;
	private BigDecimal amount;
	private String status;
	private LocalDateTime createdAt;
	
	public static TransferResponse from(Transaction transaction) {
		TransferResponse response = new TransferResponse();
		response.id = transaction.getId();
		response.sourceAccountId = transaction.getSourceAccountId();
		response.targetAccountId = transaction.getTargetAccountId();
		response.amount = transaction.getAmount();
		response.status = transaction.getStatus().name();
		response.createdAt = transaction.getCreatedAt();
		return response;
	}
	
	public UUID getId() {return id;}
	public UUID getSourceAccountId() {return sourceAccountId;}
	public UUID getTargetId() {return targetAccountId;}
	public BigDecimal getAmout() {return amount;}
	public String getStatus() {return status;}
	public LocalDateTime getCreatedAt() {return createdAt;}
}
