package com.WSEBanking.WSEBanking.api.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.WSEBanking.WSEBanking.api.DTOs.CredentialsDto;
import com.WSEBanking.WSEBanking.api.DTOs.SignUpDto;
import com.WSEBanking.WSEBanking.api.DTOs.UserDto;

import com.WSEBanking.WSEBanking.config.UserAuthenticationProvider;
import com.WSEBanking.WSEBanking.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        System.out.println("Username:" + credentialsDto.getEmail() + "Password: " + credentialsDto.getPassword());

        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));

        System.out.println("Username:" + credentialsDto.getEmail() + "Password: " + credentialsDto.getPassword());
        System.out.println("Username:" + userDto.getEmail() + "Password: " + userDto.getPassword());

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        System.out.println("Username:" + user.getEmail() + "Password: " + user.getPassword());
        System.out.println("Username:" + createdUser.getEmail() + "Password: " + createdUser.getPassword());
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
