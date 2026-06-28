package com.kildare.ledgerflow.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;



public class StatementResponse {

    private UUID accountId;
    private BigDecimal currentBalance;
    private List<TransferResponse> transactions;
    private int page;
    private int totalPages;
    private long totalElements;

    public static StatementResponse of(UUID accountId,
                                       BigDecimal currentBalance,
                                       List<TransferResponse> transactions,
                                       int page,
                                       int totalPages,
                                       long totalElements) {
        StatementResponse response = new StatementResponse();
        response.accountId = accountId;
        response.currentBalance = currentBalance;
        response.transactions = transactions;
        response.page = page;
        response.totalPages = totalPages;
        response.totalElements = totalElements;
        return response;
    }

    public UUID getAccountId() { return accountId; }
    public BigDecimal getCurrentBalance() { return currentBalance; }
    public List<TransferResponse> getTransactions() { return transactions; }
    public int getPage() { return page; }
    public int getTotalPages() { return totalPages; }
    public long getTotalElements() { return totalElements; }
}