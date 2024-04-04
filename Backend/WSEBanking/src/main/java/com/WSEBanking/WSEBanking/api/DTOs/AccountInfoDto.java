package com.WSEBanking.WSEBanking.api.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing Account Information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoDto {
    private String userId;
    private String accountName;
    private String accountNumber;
    private double balance;
}
