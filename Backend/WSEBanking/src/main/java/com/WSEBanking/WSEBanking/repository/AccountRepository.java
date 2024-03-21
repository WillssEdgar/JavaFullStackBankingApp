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

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    //Account findByUserId(Integer user_id);
    Account findById(@NonNull int id);

    Account findByAccountNumberAndUserId(String accountNumber, Integer user_id);

}
