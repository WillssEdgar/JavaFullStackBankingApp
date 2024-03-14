package com.WSEBanking.WSEBanking.api.DTOs;

import com.WSEBanking.WSEBanking.api.model.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String username;

    @NotEmpty
    private Role role;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;

}
