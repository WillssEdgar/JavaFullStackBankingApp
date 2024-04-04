package com.WSEBanking.WSEBanking.api.controller;

import com.WSEBanking.WSEBanking.api.DTOs.*;
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
    /// Controller for Accounts
    @Autowired
    private AccountService accountService;

    /**
     *  Retrieves Information about a specific account.
     *
     *  @param userId: The ID of the user associated with the account
     *  @param accountNumber: The account number
     *  @return An AccountInfoDto containing information about the account.
     */
    @GetMapping("/accounts/account")
    public ResponseEntity<AccountInfoDto> findAccountInfo(
            @RequestParam("userId") int userId,
            @RequestParam("accountNumber") String accountNumber){

        AccountInfoDto accountInfoDto = accountService.findAccountInfo(userId, accountNumber);

        if (accountInfoDto != null) {
            return ResponseEntity.ok(accountInfoDto); // Account found, return OK response
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Account not found, return 404 response
        }
    }

    /**
     * Adds a new Account
     *
     * @param newAccountDto: The DTO containing information for the new account.
     * @return The DTO representing the newly created account.
     */
    @PostMapping("/accounts/addNewAccount")
    public ResponseEntity<String> addAccount(@RequestBody NewAccountDto newAccountDto) {
        String AccountMadeSuccessfully = accountService.addAccount(newAccountDto);

        if (AccountMadeSuccessfully != null){
            return ResponseEntity.ok(AccountMadeSuccessfully);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    /**
     * Transfers funds between accounts.
     *
     * @param transfer: The DTO containing transfer details.
     * @return A string indicating the status of the transfer.
     */
    @PostMapping("/accounts/transactions/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDto transfer) {
        String TransferWasSuccessful = accountService.transfer(transfer);

        if (TransferWasSuccessful == null) {
            // Withdrawal failed, insufficient balance or other error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(TransferWasSuccessful);
    }

    /**
     * Withdrawals funds from specified account.
     *
     * @param transactionDto: The DTO containing transaction details.
     * @return A String if Successful and an empty body if Failed
     */
    @PostMapping("/accounts/transactions/withdrawal")
    public ResponseEntity<String> withdrawal(@RequestBody TransactionDto transactionDto){


        AccountDto accountDto = accountService.withdrawal(transactionDto);

        if (accountDto == null) {
            // Withdrawal failed, insufficient balance or other error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Withdrawal successful, return updated user details
        return ResponseEntity.ok("Withdrawal was Successful!");
    }

    /**
     * Deposits funds into a specified account
     *
     * @param transactionDto: The DTO containing transaction details.
     * @return A String if Successful and an empty body if Failed
     */
    @PostMapping("/accounts/transactions/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransactionDto transactionDto) {

        AccountDto accountDto = accountService.deposit(transactionDto);

        if (accountDto == null) {
            // Withdrawal failed, insufficient balance or other error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Withdrawal successful, return updated user details
        return ResponseEntity.ok("Deposit was successful!");
    }
}
