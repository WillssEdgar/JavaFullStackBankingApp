package com.WSEBanking.WSEBanking.api.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a transaction made on an account.
 * Each transaction contains information such as the transaction type, amount, date, and balance.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {

    /**
     * The unique identifier for the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The account associated with this transaction.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    /**
     * The type of transaction (e.g., deposit, withdrawal).
     */
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    /**
     * The amount involved in the transaction.
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * The date and time when the transaction occurred.
     */
    @Column(name = "transaction_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime transactionDate;

    /**
     * The balance of the account after the transaction.
     */
    @Column(name = "balance", nullable = false)
    private Double balance;

    // Constructors, getters, and setters
}
