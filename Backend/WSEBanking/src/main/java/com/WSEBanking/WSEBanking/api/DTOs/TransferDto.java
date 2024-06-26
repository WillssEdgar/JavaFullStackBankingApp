package com.WSEBanking.WSEBanking.api.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing transfer credentials.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDto {
    private String fromAccount;
    private String toAccount;
    private Double amount;
    private Integer userId;
}
