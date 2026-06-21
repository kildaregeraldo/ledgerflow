package com.kildare.ledgerflow.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = true)
	private UUID transactionId;
	
	@Column(nullable = false)
	private String action;
	
	@Column(nullable = true)
	private String detail;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime timestamp;
	
	@PrePersist
	protected void onCreate() {
		this.timestamp = LocalDateTime.now();
	}
	
	public AuditLog() {
		
	}
	
	public AuditLog(String action, String detail) {
		this.action = action;
		this.detail = detail;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public UUID getId() {
		return id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	
	
}

