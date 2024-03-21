package com.WSEBanking.WSEBanking.api.controller;

import com.WSEBanking.WSEBanking.api.DTOs.AccountDto;
import com.WSEBanking.WSEBanking.api.DTOs.CredentialsDto;
import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
import com.WSEBanking.WSEBanking.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts/account")
    public ResponseEntity<Map<String, String>> findAccountInfo(
            @RequestParam("userId") int userId,
            @RequestParam("accountNumber") String accountNumber){

        Map<String, String> account = accountService.findAccountInfo(userId, accountNumber);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<AccountDto> withdrawal(
            @RequestParam("userId") Integer userId,
            @RequestParam("withdrawalAmount")  double withdrawalAmount) {

        AccountDto accountDto = accountService.withdrawal(userId, withdrawalAmount);

        if (accountDto == null) {
            // Withdrawal failed, insufficient balance or other error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Withdrawal successful, return updated user details
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(
            @RequestParam("accountId") String accountId,
            @RequestParam("depositAmount")  String depositAmount) {

        int id = Integer.parseInt(accountId);
        double depositAmnt = Double.parseDouble(depositAmount);

        AccountDto accountDto = accountService.deposit(id, depositAmnt);

        if (accountDto == null) {
            // Withdrawal failed, insufficient balance or other error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Withdrawal successful, return updated user details
        return ResponseEntity.ok(accountDto);
    }
}
