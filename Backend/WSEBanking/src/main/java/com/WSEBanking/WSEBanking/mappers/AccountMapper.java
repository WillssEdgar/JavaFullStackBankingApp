package com.WSEBanking.WSEBanking.mappers;

import com.WSEBanking.WSEBanking.api.model.Account;
import org.mapstruct.Mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapper interface for mapping Account objects to a Map.
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {
    /**
     * Converts an Account object to a Map.
     *
     * @param account The Account object to be converted.
     * @return A Map representing the Account object.
     */
    default Map<String, String> toMap(Account account){
        if (account == null){
            return null;
        }

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(account.getId()));
        map.put("user_id", String.valueOf(account.getUser().getId()));
        map.put("accountNumber", account.getAccountNumber());
        map.put("accountName", account.getAccountName());
        map.put("balance", String.valueOf(account.getBalance()));

        return map;
    }
}
