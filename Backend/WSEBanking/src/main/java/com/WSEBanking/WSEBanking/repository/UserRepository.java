package com.WSEBanking.WSEBanking.repository;

import java.util.List;
import java.util.Optional;

import com.WSEBanking.WSEBanking.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import com.WSEBanking.WSEBanking.api.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("SELECT u.accounts FROM User u Where u.id = :userId")
    List<Account> findAccountsByUserId(@Param("userId") int userId);
}
