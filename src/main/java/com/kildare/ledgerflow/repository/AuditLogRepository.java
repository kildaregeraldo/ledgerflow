package com.kildare.ledgerflow.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kildare.ledgerflow.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

	
}
