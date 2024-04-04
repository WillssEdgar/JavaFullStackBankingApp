package com.WSEBanking.WSEBanking.repository;

import java.util.List;
import java.util.Optional;

import com.WSEBanking.WSEBanking.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import com.WSEBanking.WSEBanking.api.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for accessing User entities in the database.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Retrieves a user by username.
     *
     * @param username The username to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves a user by email.
     *
     * @param email The email address to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    User findByEmail(String email);

    /**
     * Retrieves a list of accounts associated with a user by user ID.
     *
     * @param userId The ID of the user whose accounts are to be retrieved.
     * @return A list of accounts associated with the specified user.
     */
    @Query("SELECT u.accounts FROM User u Where u.id = :userId")
    List<Account> findAccountsByUserId(@Param("userId") int userId);
}
