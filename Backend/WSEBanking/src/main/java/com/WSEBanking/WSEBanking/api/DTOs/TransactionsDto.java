package com.WSEBanking.WSEBanking.api.DTOs;

import com.WSEBanking.WSEBanking.api.model.Account;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing the transactions.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionsDto {
    private String accountNumber;
    private String transactionType;
    private Double amount;
    private Double balance;
    private LocalDateTime transactionDate;
}
