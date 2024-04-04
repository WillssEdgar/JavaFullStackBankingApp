package com.WSEBanking.WSEBanking.api.controller;

import com.WSEBanking.WSEBanking.api.DTOs.AccountInfoDto;
import com.WSEBanking.WSEBanking.api.DTOs.TransactionsDto;
import com.WSEBanking.WSEBanking.repository.AccountRepository;
import com.WSEBanking.WSEBanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

/**
 * Controller class for handling transaction-related HTTP requests.
 */
@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Retrieves transactions associated with a specific account.
     *
     * @param accountId The ID of the account.
     * @return ResponseEntity containing a list of transaction DTOs associated with the specified account.
     */
    @GetMapping("/transactions/getTransactions")
    public ResponseEntity<List<TransactionsDto>> getTransactions(
            @RequestParam("accountId") int accountId) {

        List<TransactionsDto> transactionsDto = transactionService.getTransactions(accountId);
        if (transactionsDto != null) {
            return ResponseEntity.ok(transactionsDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
