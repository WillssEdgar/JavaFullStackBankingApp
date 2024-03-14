package com.WSEBanking.WSEBanking.service;

import java.nio.CharBuffer;
import java.util.Optional;

import com.WSEBanking.WSEBanking.api.model.Role;
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

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Unknown User"));

        if (passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new BadCredentialsException("Invalid Password");
    }

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

    public UserDto findByLogin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }
}
