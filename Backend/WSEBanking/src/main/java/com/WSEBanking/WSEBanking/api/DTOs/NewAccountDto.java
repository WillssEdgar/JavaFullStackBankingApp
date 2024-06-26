package com.WSEBanking.WSEBanking.api.DTOs;


import com.WSEBanking.WSEBanking.api.model.AccountType;
import com.WSEBanking.WSEBanking.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a new account.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewAccountDto {
    private String accountName;
    private AccountType accountType;
    private Integer userId;
}
