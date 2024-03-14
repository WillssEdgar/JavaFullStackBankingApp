package com.WSEBanking.WSEBanking.api.DTOs;

import com.WSEBanking.WSEBanking.api.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Role role;
    private String email;
    private String username;
    private String password;
    private String token;
}
