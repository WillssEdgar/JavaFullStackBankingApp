 package com.WSEBanking.WSEBanking.api.controller;

 import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
 import com.WSEBanking.WSEBanking.config.UserAuthenticationProvider;
 import com.WSEBanking.WSEBanking.service.UserService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 //import org.springframework.web.bind.annotation.GetMapping;
 //import org.springframework.web.bind.annotation.RequestParam;

 import java.util.*;

 @RestController
 public class UserController {

     @Autowired
     private UserService userService;
     UserDto userDto;
     UserAuthenticationProvider userAuthenticationProvider;

     /**
      * Retrieves all accounts associated with a user.
      *
      * @param userId The ID of the user.
      * @return ResponseEntity containing a list of maps representing account information.
      */
     @GetMapping("/users/accounts")
     public ResponseEntity<List<Map<String, String>>> findAllAccountsByUserId(@RequestParam("userId") int userId){
         List<Map<String, String>> accounts = userService.findAllAccountsByUserId(userId);
         if (accounts != null) {
             return ResponseEntity.ok(accounts);
         } else {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
         }
     }

 }
