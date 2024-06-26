package com.WSEBanking.WSEBanking.api.DTOs;


import com.WSEBanking.WSEBanking.api.model.AccountType;
import com.WSEBanking.WSEBanking.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing an entire Account.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Integer id;
    private String accountNumber;
    private String accountName;
    private AccountType accountType;
    private double balance;
    private User user;
}
