package com.kildare.ledgerflow.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kildare.ledgerflow.dto.request.TransferRequest;
import com.kildare.ledgerflow.dto.response.TransferResponse;
import com.kildare.ledgerflow.model.Transaction;
import com.kildare.ledgerflow.repository.TransactionRepository;
import com.kildare.ledgerflow.service.TransferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transfers")
public class TransferController {

	private final TransferService transferService;
	private final TransactionRepository  transactionRepository;
	
	@Autowired
	public  TransferController (TransferService transferService, TransactionRepository  transactionRepository){
	
		this.transferService = transferService;
		this.transactionRepository = transactionRepository;
		
	}		
		@PostMapping
		public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request){
		
		Transaction transaction = transferService.transfer(
			request.getSourceAccountId(),
			request.getTargetAccountId(),
			request.getAmount(),
			request.getIdempotencyKey(),
			request.getActor());
			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(TransferResponse.from(transaction));
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<TransferResponse> getTransfer(@PathVariable UUID id){
			
			Transaction transaction = transactionRepository.findById(id)
					.orElseThrow(() -> new RuntimeException ("Transferência não encontrada: " + id));
			
			return ResponseEntity.ok(TransferResponse.from(transaction));
		}
	}

