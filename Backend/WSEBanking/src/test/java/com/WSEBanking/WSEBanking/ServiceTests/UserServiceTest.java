package com.WSEBanking.WSEBanking.ServiceTests;

import com.WSEBanking.WSEBanking.api.DTOs.CredentialsDto;
import com.WSEBanking.WSEBanking.api.DTOs.SignUpDto;
import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
import com.WSEBanking.WSEBanking.api.model.User;
import com.WSEBanking.WSEBanking.mappers.AccountMapper;
import com.WSEBanking.WSEBanking.mappers.UserMapper;
import com.WSEBanking.WSEBanking.repository.UserRepository;
import com.WSEBanking.WSEBanking.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.WSEBanking.WSEBanking.api.model.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private UserService userService;

    /**
     * Test case to verify login functionality with valid credentials.
     */
    @Test
    void testLogin_ValidCredentials_ReturnsUserDto() {
        // Arrange
        CredentialsDto credentialsDto = new CredentialsDto("test@example.com", "password");
        User user = new User();
        when(userRepository.findByEmail(credentialsDto.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())).thenReturn(true);
        UserDto expectedUserDto = new UserDto();
        when(userMapper.toUserDto(user)).thenReturn(expectedUserDto);

        // Act
        UserDto result = userService.login(credentialsDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUserDto, result);
    }

    /**
     * Test case to verify user registration with valid sign-up details.
     */
    @Test
    void testRegister_WithValidSignUpDto_ReturnsUserDto() {
        // Setup
        SignUpDto signUpDto = new SignUpDto("test", "example", "testexample", USER, "password", "test@example.com");
        User user = new User();
        when(userRepository.findByUsername(signUpDto.getUsername())).thenReturn(Optional.empty());
        when(userMapper.signUpToUser(signUpDto)).thenReturn(user);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword"); // Mocking encoded password
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(new UserDto()); // Mocking UserDto
        // Execute
        UserDto result = userService.register(signUpDto);
        // Verify
        assertNotNull(result);
        // Add more assertions as needed
    }
}
