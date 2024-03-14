package com.WSEBanking.WSEBanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WSEBanking.WSEBanking.api.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    // User findByUsername(String username);
    // User findByEmail(String email);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
