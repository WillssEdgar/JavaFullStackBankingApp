package com.WSEBanking.WSEBanking.ControllerTests;

import com.WSEBanking.WSEBanking.api.DTOs.TransactionsDto;
import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
import com.WSEBanking.WSEBanking.api.controller.UserController;
import com.WSEBanking.WSEBanking.config.UserAuthenticationProvider;
import com.WSEBanking.WSEBanking.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    /**
     * Test case to verify finding all accounts by user ID when accounts are found.
     */
    @Test
    public void findAllAccountsByUserId_AccountsFound_ReturnResponseEntityListOfMaps() {
        int userId = 1;
        List<Map<String, String>> mockAccounts = Collections.singletonList(Collections.singletonMap("key", "value"));
        when(userService.findAllAccountsByUserId(userId)).thenReturn(mockAccounts);

        ResponseEntity<List<Map<String, String>>> response = userController.findAllAccountsByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAccounts, response.getBody());
    }

    /**
     * Test case to verify finding all accounts by user ID when no accounts are found.
     */
    @Test
    public void findAllAccountsByUserId_NoAccountsFound_ReturnResponseEntityBadRequest() {
        int userId = 1;
        when(userService.findAllAccountsByUserId(userId)).thenReturn(null);

        ResponseEntity<List<Map<String, String>>> response = userController.findAllAccountsByUserId(userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
