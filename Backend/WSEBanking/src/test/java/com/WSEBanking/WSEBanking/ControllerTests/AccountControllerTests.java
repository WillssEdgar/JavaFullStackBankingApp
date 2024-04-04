package com.WSEBanking.WSEBanking.ControllerTests;

import com.WSEBanking.WSEBanking.api.DTOs.*;
import com.WSEBanking.WSEBanking.api.controller.AccountController;
import com.WSEBanking.WSEBanking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Tests for Account Controller
 */
@SpringBootTest
public class AccountControllerTests {
    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    /**
     * Test case to verify the behavior when the requested account information is found.
     */
    @Test
    public void findAccountInfoTests_AccountsFound_ReturnResponseEntityAccountInfoDto() {
        int userId = 1;
        String accountNumber = "00000000";
        AccountInfoDto accountInfoDto = new AccountInfoDto();
        when(accountService.findAccountInfo(userId, accountNumber)).thenReturn(accountInfoDto);

        ResponseEntity<AccountInfoDto> result = accountController.findAccountInfo(userId, accountNumber);

        assertNotNull(result); // Check if response entity is not null
        assertEquals(HttpStatus.OK, result.getStatusCode()); // Check if status code is OK

        // Check if the body of the response entity matches the expected accountInfoDto
        assertEquals(accountInfoDto, result.getBody());
    }

    /**
     * Test case to verify the behavior when the requested account information is not found.
     */
    @Test
    public void findAccountInfoTests_AccountsNotFound_ReturnResponseEntityNotFound(){
        int userId = 1;
        String accountNumber = "00000000";
        AccountInfoDto accountInfoDto = new AccountInfoDto();
        when(accountService.findAccountInfo(userId, accountNumber)).thenReturn(null);

        ResponseEntity<AccountInfoDto> result = accountController.findAccountInfo(userId, accountNumber);

        assertNotNull(result); // Check if response entity is not null
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode()); // Check if status code is NOT_FOUND

    }

    /**
     * Test case to verify the behavior when an account is successfully added.
     */
    @Test
    public void addAccountTests_AddedAccountSuccessfully_ReturnsResponseEntityString(){
        NewAccountDto newAccountDto = new NewAccountDto();
        when(accountService.addAccount(newAccountDto)).thenReturn("Successfully created a new account!");

        ResponseEntity<String> result = accountController.addAccount(newAccountDto);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    /**
     * Test case to verify the behavior when an account is not successfully added.
     */
    @Test
    public void addAccountTests_NoAccountAdded_ReturnsResponseEntityNotFound(){
        NewAccountDto newAccountDto = new NewAccountDto();
        when(accountService.addAccount(newAccountDto)).thenReturn(null);

        ResponseEntity<String> result = accountController.addAccount(newAccountDto);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

    /**
     * Test case to verify the behavior when a transfer is successful.
     */
    @Test
    public void transferTests_TransferSuccessful_ReturnsResponseEntityString(){
        TransferDto transferDto = new TransferDto();
        when(accountService.transfer(transferDto)).thenReturn("Transfer was Successful!");

        ResponseEntity<String> result = accountController.transfer(transferDto);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    /**
     * Test case to verify the behavior when a transfer failed.
     */
    @Test
    public void transferTests_TransferFailed_ReturnsResponseEntityBadRequest(){
        TransferDto transferDto = new TransferDto();
        when(accountService.transfer(transferDto)).thenReturn(null);

        ResponseEntity<String> result = accountController.transfer(transferDto);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }


    /**
     * Test case to verify the behavior when a withdrawal is successful.
     */
    @Test
    public void withdrawalTests_WithdrawalSuccessful_ReturnResponseEntityOK(){
        TransactionDto transactionDto = new TransactionDto();
        AccountDto accountDto = new AccountDto();
        when(accountService.withdrawal(transactionDto)).thenReturn(accountDto);

        ResponseEntity<String> result = accountController.withdrawal(transactionDto);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    /**
     * Test case to verify the behavior when a withdrawal failed.
     */
    @Test
    public void withdrawalTests_WithdrawalFailed_ReturnResponseEntityBadRequest(){
        TransactionDto transactionDto = new TransactionDto();
        AccountDto accountDto = new AccountDto();
        when(accountService.withdrawal(transactionDto)).thenReturn(null);

        ResponseEntity<String> result = accountController.withdrawal(transactionDto);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    /**
     * Test case to verify the behavior when a deposit is successful.
     */
    @Test
    public void depositTests_DepositSuccessful_ReturnResponseEntityOK(){
        TransactionDto transactionDto = new TransactionDto();
        AccountDto accountDto = new AccountDto();
        when(accountService.deposit(transactionDto)).thenReturn(accountDto);

        ResponseEntity<String> result = accountController.deposit(transactionDto);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    /**
     * Test case to verify the behavior when a deposit failed.
     */
    @Test
    public void depositTests_DepositFailed_ReturnResponseEntityOK(){
        TransactionDto transactionDto = new TransactionDto();
        AccountDto accountDto = new AccountDto();
        when(accountService.deposit(transactionDto)).thenReturn(null);

        ResponseEntity<String> result = accountController.deposit(transactionDto);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
