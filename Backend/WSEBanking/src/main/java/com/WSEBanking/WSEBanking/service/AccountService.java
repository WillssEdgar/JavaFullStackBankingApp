package com.WSEBanking.WSEBanking.service;

import com.WSEBanking.WSEBanking.api.DTOs.*;
import com.WSEBanking.WSEBanking.api.model.Account;
import com.WSEBanking.WSEBanking.api.model.Transaction;
import com.WSEBanking.WSEBanking.exceptions.AppException;
import com.WSEBanking.WSEBanking.mappers.AccountMapper;
import com.WSEBanking.WSEBanking.repository.AccountRepository;
import com.WSEBanking.WSEBanking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TransactionRepository transactionRepository;

    /**
     * Handles the Logic of the Transfer method in Account Controller
     *
     * @param transferDto: DTO that holds parameters needed
     * @return A string or null depending on the status of the transfer
     */
    @Transactional
    public String transfer(TransferDto transferDto){
        Optional<Account> fromAccount = accountRepository.findByAccountNumber(String.valueOf(transferDto.getFromAccount()));
        Optional<Account> toAccount = accountRepository.findByAccountNumber(String.valueOf(transferDto.getToAccount()));

        if (fromAccount.isEmpty() || toAccount.isEmpty()) {
            return null; // User not found or account doesn't exist
        }

        Account from_Account = fromAccount.get();
        Account to_Account = toAccount.get();
        Double amount = transferDto.getAmount();

        if (from_Account.getBalance() < amount){
            return "Insufficient Funds";
        }

        double fromAccountBalance = from_Account.getBalance() - amount;
        from_Account.setBalance(fromAccountBalance);

        double toAccountBalance = to_Account.getBalance() + amount;
        to_Account.setBalance(toAccountBalance);

        accountRepository.save(from_Account);
        accountRepository.save(to_Account);

        Transaction fromAccountTransaction = new Transaction();
        fromAccountTransaction.setAccount(from_Account);
        fromAccountTransaction.setTransactionType("TRANSFER");
        fromAccountTransaction.setTransactionDate(LocalDateTime.now());
        fromAccountTransaction.setAmount(transferDto.getAmount());
        fromAccountTransaction.setBalance(fromAccountBalance);
        transactionRepository.save(fromAccountTransaction);

        Transaction toAccountTransaction = new Transaction();
        toAccountTransaction.setAccount(from_Account);
        toAccountTransaction.setTransactionType("TRANSFER");
        toAccountTransaction.setTransactionDate(LocalDateTime.now());
        toAccountTransaction.setAmount(transferDto.getAmount());
        toAccountTransaction.setBalance(toAccountBalance);
        transactionRepository.save(toAccountTransaction);


        return "Transfer was Successful!";
    }

    /**
     * Handles the Logic of the Withdrawal method in Account Controller
     *
     * @param transactionDto: DTO that holds the parameters needed to withdrawal funds
     * @return AccountDto
     */
    @Transactional
    public AccountDto withdrawal(TransactionDto transactionDto) {
        System.out.println("transactionDto: " + transactionDto);
        // Assuming you have the user's account details (e.g., fetched by username or ID)
        Optional<Account> optionalAccount = accountRepository.findById(transactionDto.getAccountId()); // Fetch the user's account
        System.out.println("Optional Account: " + optionalAccount);
        if (optionalAccount.isEmpty()) {
            return null; // User not found or account doesn't exist
        }

        Account account = optionalAccount.get();
        System.out.println("Account: " + account);
        // Check if withdrawal amount is valid
        if (transactionDto.getAmount() <= 0 || transactionDto.getAmount() > account.getBalance()) {
            return null; // Invalid withdrawal amount or insufficient balance
        }

        // Update account balance
        double newBalance = account.getBalance() - transactionDto.getAmount();
        account.setBalance(newBalance);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(newBalance);
        transactionRepository.save(transaction);

        // Save the updated account details
        accountRepository.save(account);

        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountName(account.getAccountName());
        accountDto.setBalance(account.getBalance());

        return accountDto;
    }

    /**
     * Handles the Logic of the Deposit method in the Account Controller
     *
     * @param transactionDto: DTO that holds the parameters needed to deposit funds
     * @return AccountDto
     */
    @Transactional
    public AccountDto deposit(TransactionDto transactionDto) {

        // Assuming you have the user's account details (e.g., fetched by username or ID)
        Optional<Account> optionalAccount = accountRepository.findById(transactionDto.getAccountId());

        if (optionalAccount.isEmpty()) {
            return null; // User not found or account doesn't exist
        }

        Account account = optionalAccount.get();

        // Check if withdrawal amount is valid
        if (transactionDto.getAmount() <= 0) {
            return null; // Invalid withdrawal amount or insufficient balance
        }

        // Update account balance
        double newBalance = account.getBalance() + transactionDto.getAmount();
        account.setBalance(newBalance);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType("DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(newBalance);
        transactionRepository.save(transaction);

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

    /**
     * Handles the logic for the AddAccount method in the Account Controller
     *
     * @param newAccountDto: DTO that holds the parameters needed to add an account
     * @return String
     */
    public String addAccount(NewAccountDto newAccountDto) {
        Optional<Account> optionalAccount = accountRepository.findByAccountName(newAccountDto.getAccountName());

        if (optionalAccount.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        Account account = new Account();
        account.setAccountName(newAccountDto.getAccountName());
        account.setAccountNumber(createAccountNumber());
        account.setAccountType(newAccountDto.getAccountType());
        account.setUserId(newAccountDto.getUserId());
        account.setBalance(0.00);

        account = accountRepository.save(account);

        return "Successfully created a new account!";
    }

    /**
     * Handles the logic of the findAccountInfo in the Account Controller
     *
     * @param userId: User ID number of the account needed
     * @param accountNumber: Account number of the user
     * @return AccountInfoDto
     */
    public AccountInfoDto findAccountInfo(int userId, String accountNumber) {
        Account account = accountRepository.findByAccountNumberAndUserId(accountNumber, userId);

        if (account == null) {
            return null; // User not found or account doesn't exist
        }

        AccountInfoDto accountInfoDto = new AccountInfoDto();
        accountInfoDto.setAccountName(account.getAccountName());
        accountInfoDto.setAccountNumber(account.getAccountNumber());
        accountInfoDto.setUserId(account.getUserId().toString());
        accountInfoDto.setBalance(account.getBalance());
        return accountInfoDto;

    }

    /**
     * Creates an account number that is not already in the database
     *
     * @return account number
     */
    public String createAccountNumber() {


        while(true) {
            Random random = new Random();
            StringBuilder accountNumberBuilder = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                int digit = random.nextInt(10);
                accountNumberBuilder.append(digit);
            }

            Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumberBuilder.toString());

            if (optionalAccount.isEmpty()) {
                return accountNumberBuilder.toString();
            }
        }
    }
}
