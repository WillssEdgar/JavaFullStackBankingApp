package com.WSEBanking.WSEBanking.repository;

import com.WSEBanking.WSEBanking.api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing transactions.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    /**
     * Retrieves all transactions associated with a specific account.
     *
     * @param accountId The ID of the account.
     * @return A list of transactions associated with the specified account.
     */
    List<Transaction> findAllByAccountId(int accountId);

}
