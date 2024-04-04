package com.WSEBanking.WSEBanking.ControllerTests;

import com.WSEBanking.WSEBanking.api.DTOs.CredentialsDto;
import com.WSEBanking.WSEBanking.api.DTOs.SignUpDto;
import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
import com.WSEBanking.WSEBanking.api.controller.AuthController;
import com.WSEBanking.WSEBanking.config.UserAuthenticationProvider;
import com.WSEBanking.WSEBanking.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private UserAuthenticationProvider userAuthenticationProvider;

    @InjectMocks
    private AuthController authController;

    /**
     * Test case to verify login with valid credentials returns ResponseEntity with UserDto and token.
     */
    @Test
    public void login_ValidCredentials_ReturnsResponseEntityWithUserDtoAndToken() {
        // Arrange
        CredentialsDto credentialsDto = new CredentialsDto("username", "password");
        UserDto userDto = new UserDto();
        when(userService.login(credentialsDto)).thenReturn(userDto);
        when(userAuthenticationProvider.createToken(userDto)).thenReturn("token");

        // Act
        ResponseEntity<UserDto> response = authController.login(credentialsDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
        assertEquals("token", userDto.getToken());
    }

    /**
     * Test case to verify login with invalid credentials returns ResponseEntity with status BAD_REQUEST.
     */
    @Test
    public void login_InvalidCredentials_ReturnsResponseEntityBadRequest() {
        // Arrange
        CredentialsDto credentialsDto = new CredentialsDto("username", "password");
        when(userService.login(credentialsDto)).thenReturn(null);

        // Act
        ResponseEntity<UserDto> response = authController.login(credentialsDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Test case to verify registration with valid user data returns ResponseEntity with created UserDto and token.
     */
    @Test
    public void register_ValidUserData_ReturnsResponseEntityWithCreatedUserDtoAndToken() {
        // Arrange
        SignUpDto signUpDto = new SignUpDto();
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(1);
        when(userService.register(signUpDto)).thenReturn(createdUserDto);
        when(userAuthenticationProvider.createToken(createdUserDto)).thenReturn("token");

        // Act
        ResponseEntity<UserDto> response = authController.register(signUpDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdUserDto, response.getBody());
        assertEquals("token", createdUserDto.getToken());
    }

    /**
     * Test case to verify registration failure returns ResponseEntity with status BAD_REQUEST.
     */
    @Test
    public void register_UserRegistrationFailed_ReturnsResponseEntityInternalServerError() {
        // Arrange
        SignUpDto signUpDto = new SignUpDto();
        when(userService.register(signUpDto)).thenReturn(null);

        // Act
        ResponseEntity<UserDto> response = authController.register(signUpDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }


}

