package com.WSEBanking.WSEBanking.api.DTOs;


import com.WSEBanking.WSEBanking.api.model.AccountType;
import com.WSEBanking.WSEBanking.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing transaction credentials.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private Integer accountId;
    private double amount;
}
