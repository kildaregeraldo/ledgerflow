package com.kildare.ledgerflow.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kildare.ledgerflow.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

	Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
	
	@Query("SELECT t FROM Transaction t " +
			"WHERE t.sourceAccountId = :accountId OR t.targetAccountId = :accountId " +
			"ORDER BY t.createdAt DESC")
	Page<Transaction> findStatementByAccountId(@Param("accountId") UUID accountId, Pageable pageable);
}
