package com.WSEBanking.WSEBanking.api.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
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

  /**
   * Handles user login.
   *
   * @param credentialsDto The DTO containing user credentials.
   * @return ResponseEntity containing the authenticated user DTO along with an
   *         authentication token.
   */
  @PostMapping("/login")
  public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
    UserDto userDto = userService.login(credentialsDto);
    if (userDto == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    userDto.setToken(userAuthenticationProvider.createToken(userDto));

    return ResponseEntity.ok(userDto);
  }

  /**
   * Handles user registration.
   *
   * @param user The DTO containing user registration data.
   * @return ResponseEntity containing the created user DTO along with an
   *         authentication token.
   */
  @PostMapping("/register")
  public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
    UserDto createdUser = userService.register(user);
    if (createdUser == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    createdUser.setToken(userAuthenticationProvider.createToken(createdUser));

    return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
  }
}
