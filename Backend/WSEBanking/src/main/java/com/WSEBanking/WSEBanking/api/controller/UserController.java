 package com.WSEBanking.WSEBanking.api.controller;

 import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
 import com.WSEBanking.WSEBanking.api.model.Account;
 import com.WSEBanking.WSEBanking.config.UserAuthenticationProvider;
 import com.WSEBanking.WSEBanking.service.UserService;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
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

     @GetMapping("/users/accounts")
     public ResponseEntity<List<Map<String, String>>> findAllAccountsByUserId(@RequestParam("userId") int userId){
         List<Map<String, String>> accounts = userService.findAllAccountsByUserId(userId);
         return ResponseEntity.ok(accounts);
     }

     @GetMapping("/messages")
     public ResponseEntity<List<String>> messages() {
         return ResponseEntity.ok(Arrays.asList("first", "Second"));
     }

 }
