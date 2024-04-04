package com.WSEBanking.WSEBanking.repository;

import com.WSEBanking.WSEBanking.api.model.Account;

import com.WSEBanking.WSEBanking.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * The repository interface for managing Account entities.
 * This interface provides methods to perform CRUD operations
 * and custom queries on Account entities.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Finds an account by its ID.
     *
     * @param id The ID of the account to find.
     * @return The account with the specified ID, if found.
     * @throws IllegalArgumentException if the given ID is null.
     */
    Optional<Account> findById(@NonNull int id);

    /**
     * Finds an account by its account number.
     *
     * @param accountNumber The account number of the account to find.
     * @return An Optional containing the account with the specified account number, if found.
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Finds an account by its account name.
     *
     * @param accountName The account name of the account to find.
     * @return An Optional containing the account with the specified account name, if found.
     */
    Optional<Account> findByAccountName(String accountName);

    /**
     * Finds an account by its account number and user ID.
     *
     * @param accountNumber The account number of the account to find.
     * @param userId        The ID of the user associated with the account.
     * @return The account with the specified account number and user ID, if found.
     */
    Account findByAccountNumberAndUserId(String accountNumber, Integer userId);
}
