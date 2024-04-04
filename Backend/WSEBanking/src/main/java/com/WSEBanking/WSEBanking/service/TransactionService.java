package com.WSEBanking.WSEBanking.service;

import com.WSEBanking.WSEBanking.api.DTOs.TransactionsDto;
import com.WSEBanking.WSEBanking.api.model.Account;
import com.WSEBanking.WSEBanking.api.model.Transaction;
import com.WSEBanking.WSEBanking.repository.AccountRepository;
import com.WSEBanking.WSEBanking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing transactions.
 */
@RequiredArgsConstructor
@Service
public class TransactionService {
    @Autowired
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    /**
     * Retrieves transactions associated with a specific account.
     *
     * @param accountId The ID of the account.
     * @return A list of transaction DTOs associated with the specified account.
     */
    public List<TransactionsDto> getTransactions(int accountId) {
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isEmpty()){
            return Collections.emptyList();
        }

        List<Transaction> transactions = transactionRepository.findAllByAccountId(accountId);
        if (transactions == null) {
            return Collections.emptyList();
        }


        // Map Transaction entities to TransactionDto objects
        List<TransactionsDto> transactionsDtoList = transactions.stream()
                .map(this::mapToTransactionsDto)
                .collect(Collectors.toList());
        return transactionsDtoList;
    }

    /**
     * Maps a Transaction entity to a TransactionDto object.
     *
     * @param transaction The Transaction entity.
     * @return A TransactionDto object representing the transaction.
     */
    private TransactionsDto mapToTransactionsDto(Transaction transaction) {
        TransactionsDto transactionsDto = new TransactionsDto();
        transactionsDto.setAccountNumber(transaction.getAccount().getAccountNumber());
        transactionsDto.setTransactionDate(transaction.getTransactionDate());
        transactionsDto.setTransactionType(transaction.getTransactionType());
        transactionsDto.setAmount(transaction.getAmount());
        transactionsDto.setBalance(transaction.getBalance());
        return transactionsDto;
    }
}
