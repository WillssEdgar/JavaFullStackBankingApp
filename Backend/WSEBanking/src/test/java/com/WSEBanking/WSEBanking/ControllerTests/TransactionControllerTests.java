package com.WSEBanking.WSEBanking.ControllerTests;

import com.WSEBanking.WSEBanking.api.DTOs.TransactionDto;
import com.WSEBanking.WSEBanking.api.DTOs.TransactionsDto;
import com.WSEBanking.WSEBanking.api.controller.TransactionController;
import com.WSEBanking.WSEBanking.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionControllerTests {
    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    /**
     * Test case to verify retrieving transactions when transactions are found.
     */
    @Test
    public void getTransactionsTest_TransactionsFound_ReturnResponseEntityListOfTransactionDto() {
        // Arrange
        int accountId = 1;
        List<TransactionsDto> mockTransactions = Collections.singletonList(new TransactionsDto(/* initialize with test data */));
        when(transactionService.getTransactions(accountId)).thenReturn(mockTransactions);

        // Act
        ResponseEntity<List<TransactionsDto>> response = transactionController.getTransactions(accountId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTransactions, response.getBody());
    }

    /**
     * Test case to verify retrieving transactions when no transactions are found.
     */
    @Test
    public void getTransactionsTest_TransactionsNotFound_ReturnResponseEntityListOfTransactionDto() {
        // Arrange
        int accountId = 1;
        List<TransactionsDto> mockTransactions = Collections.singletonList(new TransactionsDto(/* initialize with test data */));
        when(transactionService.getTransactions(accountId)).thenReturn(null);

        // Act
        ResponseEntity<List<TransactionsDto>> response = transactionController.getTransactions(accountId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
