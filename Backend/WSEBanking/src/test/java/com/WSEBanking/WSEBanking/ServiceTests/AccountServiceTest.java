package com.WSEBanking.WSEBanking.ServiceTests;

import com.WSEBanking.WSEBanking.api.DTOs.*;
import com.WSEBanking.WSEBanking.api.model.Account;
import com.WSEBanking.WSEBanking.api.model.AccountType;
import com.WSEBanking.WSEBanking.api.model.Transaction;
import com.WSEBanking.WSEBanking.exceptions.AppException;
import com.WSEBanking.WSEBanking.mappers.AccountMapper;
import com.WSEBanking.WSEBanking.repository.AccountRepository;
import com.WSEBanking.WSEBanking.repository.TransactionRepository;
import com.WSEBanking.WSEBanking.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private AccountService accountService;

    /**
     * Reset the mocks before each test.
     */
    @BeforeEach
    public void setUp() {
        // Reset the mocks before each test
        reset(accountRepository);
        reset(transactionRepository);
    }

    /**
     * Test case to verify transfer with insufficient funds.
     */
    @Test
    public void testTransfer_InsufficientFunds_ReturnsErrorMessage() {
        // Arrange
        TransferDto transferDto = new TransferDto("11111111", "00000000", 1000.00, 22);
        Account fromAccount = new Account();
        fromAccount.setBalance(500.00); // Setting a lower balance than the transfer amount
        Account toAccount = new Account();
        when(accountRepository.findByAccountNumber("11111111")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNumber("00000000")).thenReturn(Optional.of(toAccount));

        // Act
        String result = accountService.transfer(transferDto);

        // Assert
        assertEquals("Insufficient Funds", result);
    }

    /**
     * Test case to verify successful transfer.
     */
    @Test
    public void testTransfer_SuccessfulTransfer_ReturnsSuccessMessage() {
        // Arrange
        TransferDto transferDto = new TransferDto("11111111", "00000000", 0.00, 22 );
        Account fromAccount = new Account();
        fromAccount.setBalance(1000.00); // Sufficient balance for transfer
        Account toAccount = new Account();
        when(accountRepository.findByAccountNumber("11111111")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNumber("00000000")).thenReturn(Optional.of(toAccount));


        // Act
        String result = accountService.transfer(transferDto);
        // Assert
        assertEquals("Transfer was Successful!", result);
    }

    /**
     * Test case for transfer with non-existent accounts.
     */
    @Test
    public void testTransfer_NonExistentAccount_ReturnsNull() {
        // Arrange
        TransferDto transferDto = new TransferDto("11111111", "00000000", 100.00, 22);
        when(accountRepository.findByAccountNumber("11111111")).thenReturn(Optional.empty());
        when(accountRepository.findByAccountNumber("00000000")).thenReturn(Optional.empty());

        // Act
        String result = accountService.transfer(transferDto);

        // Assert
        assertNull(result);
    }

    /**
     * Test case for withdrawal with valid amount.
     */
    @Test
    public void withdrawal_ValidAmount_ReturnsAccountDtoWithUpdatedBalance() {
        // Arrange
        TransactionDto transactionDto = new TransactionDto(1, 50.0);
        Account account = new Account();
        account.setId(1);
        account.setBalance(100.0);
        when(accountRepository.findById(transactionDto.getAccountId())).thenReturn(Optional.of(account));

        // Act
        AccountDto result = accountService.withdrawal(transactionDto);

        // Assert
        assertNotNull(result);
        assertEquals(50.0, result.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    /**
     * Test case for withdrawal with invalid amount.
     */
    @Test
    public void withdrawal_InvalidAmount_ReturnsNull() {
        // Arrange
        TransactionDto transactionDto = new TransactionDto(1, -50.0);
        Account account = new Account();
        account.setId(1);
        account.setBalance(100.0);
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        // Act
        AccountDto result = accountService.withdrawal(transactionDto);

        // Assert
        assertNull(result);
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(accountRepository, never()).save(any(Account.class));
    }

    /**
     * Test case for withdrawal with insufficient balance.
     */
    @Test
    public void withdrawal_InsufficientBalance_ReturnsNull() {
        // Arrange
        TransactionDto transactionDto = new TransactionDto(1, 200.0);
        Account account = new Account();
        account.setId(1);
        account.setBalance(100.0);
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        // Act
        AccountDto result = accountService.withdrawal(transactionDto);

        // Assert
        assertNull(result);
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(accountRepository, never()).save(any(Account.class));
    }

    /**
     * Test case for deposit with valid amount.
     */
    @Test
    public void deposit_ValidAmount_ReturnsAccountDtoWithUpdatedBalance() {
        // Arrange
        TransactionDto transactionDto = new TransactionDto(1, 50.0);
        Account account = new Account();
        when(accountRepository.findById(transactionDto.getAccountId())).thenReturn(Optional.of(account));

        // Act
        AccountDto result = accountService.deposit(transactionDto);

        // Assert
        assertNotNull(result);
        assertEquals(50.0, result.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    /**
     * Test case for deposit with invalid amount.
     */
    @Test
    public void deposit_InvalidAmount_ReturnsNull() {
        // Arrange
        TransactionDto transactionDto = new TransactionDto(1, -50.0);
        Account account = new Account();
        account.setId(1);
        account.setBalance(100.0);
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        // Act
        AccountDto result = accountService.deposit(transactionDto);

        // Assert
        assertNull(result);
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(accountRepository, never()).save(any(Account.class));
    }

    /**
     * Test case for deposit with insufficient balance amount.
     */
    @Test
    public void deposit_InsufficientBalance_ReturnsNull() {
        // Arrange
        TransactionDto transactionDto = new TransactionDto(1, 200.0);
        Account account = new Account();
        account.setId(1);
        account.setBalance(100.0);
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        // Act
        AccountDto result = accountService.deposit(transactionDto);

        // Assert
        assertNull(result);
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(accountRepository, never()).save(any(Account.class));
    }

    /**
     * Test case for find account info success.
     */
    @Test
    public void testFindAccountInfo_Success() {
        Account account = new Account();
        account.setAccountName("TestAccount");
        account.setAccountNumber("12345678");
        account.setUserId(123);
        account.setBalance(100.0);

        when(accountRepository.findByAccountNumberAndUserId(account.getAccountNumber(), account.getUserId())).thenReturn(account);

        AccountInfoDto result = accountService.findAccountInfo(123, "12345678");

        assertEquals("TestAccount", result.getAccountName());
        assertEquals("12345678", result.getAccountNumber());
        assertEquals("123", result.getUserId());
        assertEquals(100.0, result.getBalance());
    }

    /**
     * Test case for find account info account not found.
     */
    @Test
    public void testFindAccountInfo_AccountNotFound() {
        when(accountRepository.findByAccountNumberAndUserId(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);

        AccountInfoDto result = accountService.findAccountInfo(123, "12345678");

        assertNull(result);
    }

    /**
     * Test case for add account success.
     */
    @Test
    public void testAddAccount_Success() {
        // Mocking the behavior of findByAccountName
        Mockito.when(accountRepository.findByAccountName(Mockito.anyString())).thenReturn(Optional.empty());

        NewAccountDto newAccountDto = new NewAccountDto();
        newAccountDto.setAccountName("TestAccount");
        newAccountDto.setAccountType(AccountType.SAVINGS);
        newAccountDto.setUserId(123);

        String result = accountService.addAccount(newAccountDto);
        assertEquals("Successfully created a new account!", result);
    }

    /**
     * Test case for add account already exists.
     */
    @Test
    public void testAddAccount_AccountAlreadyExists() {
        // Mocking the behavior of findByAccountName
        Mockito.when(accountRepository.findByAccountName(Mockito.anyString())).thenReturn(Optional.of(new Account()));

        NewAccountDto newAccountDto = new NewAccountDto();
        newAccountDto.setAccountName("ExistingAccount");
        newAccountDto.setAccountType(AccountType.SAVINGS);
        newAccountDto.setUserId(123);

        Exception exception = assertThrows(AppException.class, () -> {
            accountService.addAccount(newAccountDto);
        });

        assertEquals("Login already exists", exception.getMessage());
    }

    /**
     * Test case for create account number.
     */
    @Test
    public void testCreateAccountNumber() {
        String accountNumber1 = accountService.createAccountNumber();
        String accountNumber2 = accountService.createAccountNumber();

        assertNotEquals(accountNumber1, accountNumber2);
    }
}
