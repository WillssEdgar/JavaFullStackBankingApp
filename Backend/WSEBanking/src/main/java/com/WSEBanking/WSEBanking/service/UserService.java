package com.WSEBanking.WSEBanking.service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.WSEBanking.WSEBanking.api.model.Account;
import com.WSEBanking.WSEBanking.api.model.Role;
import com.WSEBanking.WSEBanking.mappers.AccountMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.WSEBanking.WSEBanking.api.DTOs.CredentialsDto;
import com.WSEBanking.WSEBanking.api.DTOs.SignUpDto;
import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
import com.WSEBanking.WSEBanking.api.model.User;
import com.WSEBanking.WSEBanking.exceptions.AppException;
import com.WSEBanking.WSEBanking.mappers.UserMapper;
import com.WSEBanking.WSEBanking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class responsible for managing user-related operations.
 */
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;

    /**
     * Authenticates a user based on provided credentials.
     *
     * @param credentialsDto The DTO containing user credentials.
     * @return The DTO representing the authenticated user.
     * @throws UsernameNotFoundException if the user is not found.
     * @throws BadCredentialsException   if the provided password is invalid.
     */
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByEmail(credentialsDto.getEmail());
        System.out.println("User" + user);
        if (user != null) {

            if (passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
                return userMapper.toUserDto(user);
            }
            throw new BadCredentialsException("Invalid Password");
        }
        throw new UsernameNotFoundException("No username not found!");
    }

    /**
     * Registers a new user.
     *
     * @param userDto The DTO containing user registration data.
     * @return The DTO representing the registered user.
     * @throws AppException if the username already exists.
     */
    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        user.setRole(Role.USER);
        user.setUsername(userDto.getUsername());
        user.setFirstname(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    /**
     * Finds a user by username.
     *
     * @param username The username of the user to find.
     * @return The DTO representing the found user.
     * @throws AppException if the user is not found.
     */
    public UserDto findByLogin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    /**
     * Finds all accounts associated with a user.
     *
     * @param userId The ID of the user.
     * @return A list of maps containing account information.
     */
    public List<Map<String, String>> findAllAccountsByUserId(int userId){
        List<Account> accounts = userRepository.findAccountsByUserId(userId);

        return accounts.stream()
                .map(accountMapper::toMap)
                .collect(Collectors.toList());
    }
}
