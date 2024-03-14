// package com.WSEBanking.WSEBanking.api.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// //import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// //import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import java.util.Map;

// @RestController
// @CrossOrigin(origins = "http://localhost:5173")
// public class UserController {

// // @GetMapping("/user")
// // public User getUser(@RequestParam Integer id) {
// // User user = userService.getUser(id);
// // return user;
// // }

// private static final Logger logger =
// LoggerFactory.getLogger(UserController.class);

// @PostMapping("/api/auth/login")
// public ResponseEntity<String> loginUser(@RequestBody Map<String, String>
// loginRequest) {
// String email = loginRequest.get("email");
// String password = loginRequest.get("password");

// // Log the received data
// logger.info("Received login request. Email: {}, Password: {}", email,
// password);

// return ResponseEntity.ok("Login sucessful");
// }

// }
