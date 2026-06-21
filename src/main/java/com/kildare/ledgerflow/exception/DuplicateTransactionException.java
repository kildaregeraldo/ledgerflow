package com.kildare.ledgerflow.exception;

public class DuplicateTransactionException extends RuntimeException{

	public DuplicateTransactionException(String idempotencyKey) {
		super("Transação duplicada detectada. Chave de idempotência: " + idempotencyKey);
	}
}
