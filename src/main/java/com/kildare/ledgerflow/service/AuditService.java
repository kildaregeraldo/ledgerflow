package com.kildare.ledgerflow.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kildare.ledgerflow.model.AuditLog;
import com.kildare.ledgerflow.repository.AuditLogRepository;

@Service
public class AuditService {

	private final AuditLogRepository auditLogRepository;
	
	@Autowired
	public AuditService(AuditLogRepository auditLogRepository) {
		this.auditLogRepository = auditLogRepository;
	}
	
	/**
	@param transactionId id da transação relacionada (pode ser null se a falha ocorreu antes da Transaction ser criada)
	
    @param action        tipo do evento (ex: "TRANSFER_SUCCESS")
    
    @param detail        mensagem descritiva para contexto adicional
   */

	
	public void log(UUID transactionId, String action, String detail) {
		AuditLog auditLog = new AuditLog(action, detail);
		auditLog.setTransactionId(transactionId);
		auditLogRepository.save(auditLog);
	}
	
}
