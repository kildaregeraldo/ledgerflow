package com.kildare.ledgerflow.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientBalanceException extends RuntimeException {

	public InsufficientBalanceException(UUID accountId, BigDecimal requestAmount, BigDecimal availableBalance) {
		super("Saldo Insuficiente: " + accountId +
				": disponivel R$" + availableBalance +
				", solicitado R$" + requestAmount);
	}
}
