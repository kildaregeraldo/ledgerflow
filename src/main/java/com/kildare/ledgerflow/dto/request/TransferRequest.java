package com.kildare.ledgerflow.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransferRequest {

	@NotNull(message = "Conta de origem é obrigatória")
	private UUID sourceAccountId;
	
	@NotNull(message = "Conta de destino é obrigatória")
	private UUID targetAccountId;
	
	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
	private BigDecimal amount;
	
	@NotBlank(message = "Chave de idempotência é obrigatória")
	private String idempotencyKey;
	
	@NotBlank(message = "Actor é obrigatório")
	private String actor;
	
	public TransferRequest() {}
	
	public UUID getSourceAccountId() {return sourceAccountId;}
	public  void setSourceAccountId (UUID sourceAccountId) { this.sourceAccountId = sourceAccountId;}
	
	public UUID getTargetAccountId() {return targetAccountId;}
	public void setTargetAccountId(UUID targetAccountId) { this.targetAccountId = targetAccountId;}
	
	public BigDecimal getAmount() {return amount;}
	public void setAmount(BigDecimal amount) {this.amount = amount;}
	
	public String getIdempotencyKey() {return idempotencyKey;}
	public void setIdempotencyKey(String IdempotencyKey) { this.idempotencyKey = idempotencyKey; }
	
	public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
}
