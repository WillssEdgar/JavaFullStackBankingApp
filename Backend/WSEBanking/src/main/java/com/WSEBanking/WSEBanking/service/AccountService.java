package com.WSEBanking.WSEBanking.service;

import com.WSEBanking.WSEBanking.api.DTOs.AccountDto;
import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
import com.WSEBanking.WSEBanking.api.model.Account;
import com.WSEBanking.WSEBanking.mappers.AccountMapper;
import com.WSEBanking.WSEBanking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountDto withdrawal(Integer userId, double withdrawalAmount) {
        // Assuming you have the user's account details (e.g., fetched by username or ID)
        Optional<Account> optionalAccount = accountRepository.findById(userId); // Fetch the user's account

        if (optionalAccount.isEmpty()) {
            return null; // User not found or account doesn't exist
        }

        Account account = optionalAccount.get();

        // Check if withdrawal amount is valid
        if (withdrawalAmount <= 0 || withdrawalAmount > account.getBalance()) {
            return null; // Invalid withdrawal amount or insufficient balance
        }

        // Update account balance
        double newBalance = account.getBalance() - withdrawalAmount;
        account.setBalance(newBalance);

        // Save the updated account details
        accountRepository.save(account);

        // Prepare and return updated user details
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountName(account.getAccountName());
        accountDto.setBalance(account.getBalance());

        return accountDto;
    }

    public Map<String, String> findAccountInfo(int userId, String accountNumber) {
        Account account = accountRepository.findByAccountNumberAndUserId(accountNumber, userId);

        if (account == null) {
            return null; // User not found or account doesn't exist
        }

        return accountMapper.toMap(account);

    }

    @Transactional
    public AccountDto deposit(Integer accountId, double depositAmount) {
        // Assuming you have the user's account details (e.g., fetched by username or ID)
        Optional<Account> optionalAccount = accountRepository.findById(accountId); // Fetch the user's account

        if (optionalAccount.isEmpty()) {
            return null; // User not found or account doesn't exist
        }

        Account account = optionalAccount.get();

        // Check if withdrawal amount is valid
        if (depositAmount <= 0) {
            return null; // Invalid withdrawal amount or insufficient balance
        }

        // Update account balance
        double newBalance = account.getBalance() + depositAmount;
        account.setBalance(newBalance);

        // Save the updated account details
        accountRepository.save(account);

        // Prepare and return updated user details
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountName(account.getAccountName());
        accountDto.setBalance(account.getBalance());

        return accountDto;
    }
}
