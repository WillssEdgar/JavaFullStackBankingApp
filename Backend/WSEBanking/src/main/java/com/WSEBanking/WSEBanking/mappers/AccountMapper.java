package com.WSEBanking.WSEBanking.mappers;

import com.WSEBanking.WSEBanking.api.model.Account;
import org.mapstruct.Mapper;

import java.util.HashMap;
import java.util.Map;


@Mapper(componentModel = "spring")
public interface AccountMapper {
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
