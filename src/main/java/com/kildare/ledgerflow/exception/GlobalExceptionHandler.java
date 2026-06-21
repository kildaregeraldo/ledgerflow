package com.kildare.ledgerflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.kildare.ledgerflow.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex, WebRequest request){
		
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				ex.getMessage(),
				request.getDescription(false));
	
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	
}
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex, WebRequest request){
		
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.UNPROCESSABLE_ENTITY.value(),
				ex.getMessage(),
				request.getDescription(false));
	
	return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
}

	@ExceptionHandler(DuplicateTransactionException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateTransaction (DuplicateTransactionException ex, WebRequest request){
		
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.CONFLICT.value(),
				ex.getMessage(),
				request.getDescription(false));
	
	return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

	}
}