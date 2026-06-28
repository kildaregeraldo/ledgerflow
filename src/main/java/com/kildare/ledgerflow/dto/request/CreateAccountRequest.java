package com.kildare.ledgerflow.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {

	@NotBlank(message = "Nome do titular é obrigatório")
	private String holderName;
	
	@NotNull(message = "Saldo inicial é obrigatório")
	@DecimalMin(value = "0.0", inclusive = true, message = "Saldo inicial não pode ser negativo")
	private BigDecimal initialBalance;
	
	public CreateAccountRequest() {}
	
	public String getHolderName() {return holderName;}
	public void setHolderName(String holderName) {this.holderName = holderName ;}
	
	public BigDecimal getInitialBalance() {return initialBalance;}
	public void setInitialBalance(BigDecimal initialBalance) {this.initialBalance = initialBalance;}
	
	
}
