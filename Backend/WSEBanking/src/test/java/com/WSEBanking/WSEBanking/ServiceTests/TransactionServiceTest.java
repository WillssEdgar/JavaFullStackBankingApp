package com.WSEBanking.WSEBanking.ServiceTests;

import com.WSEBanking.WSEBanking.api.DTOs.TransactionsDto;
import com.WSEBanking.WSEBanking.api.model.Account;
import com.WSEBanking.WSEBanking.api.model.AccountType;
import com.WSEBanking.WSEBanking.api.model.Transaction;
import com.WSEBanking.WSEBanking.api.model.User;
import com.WSEBanking.WSEBanking.mappers.AccountMapper;
import com.WSEBanking.WSEBanking.mappers.UserMapper;
import com.WSEBanking.WSEBanking.repository.AccountRepository;
import com.WSEBanking.WSEBanking.repository.TransactionRepository;
import com.WSEBanking.WSEBanking.repository.UserRepository;
import com.WSEBanking.WSEBanking.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    /**
     * Test case to verify getting transactions when the account is not found.
     */
    @Test
    public void testGetTransactions_AccountNotFound_ReturnsEmptyList() {
        // Arrange
        int accountId = 1;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act
        List<TransactionsDto> result = transactionService.getTransactions(accountId);

        // Assert
        assertNotNull(result);
        assertEquals(Collections.emptyList(), result);
    }

    /**
     * Test case to verify getting transactions when no transactions are found.
     */
    @Test
    public void testGetTransactions_NoTransactionsFound_ReturnsEmptyList() {
        // Arrange
        int accountId = 1;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.findAllByAccountId(accountId)).thenReturn(null);

        // Act
        List<TransactionsDto> result = transactionService.getTransactions(accountId);

        // Assert
        assertNotNull(result);
        assertEquals(Collections.emptyList(), result);
    }

    /**
     * Test case to verify getting transactions when transactions are found.
     */
    @Test
    public void testGetTransactions_TransactionsFound_shouldReturnListOfTransactionsDto() {
        // Arrange
        int accountId = 1;
        Account account = new Account();
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.findAllByAccountId(accountId)).thenReturn(List.of(transaction));

        // Act
        List<TransactionsDto> result = transactionService.getTransactions(accountId);

        // Assert
        assertEquals(1, result.size());
        verify(accountRepository, times(1)).findById(accountId);
        verify(transactionRepository, times(1)).findAllByAccountId(accountId);
    }
}
