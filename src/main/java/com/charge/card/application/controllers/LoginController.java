package com.charge.card.application.controllers;

import com.charge.card.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/login")
    public ResponseEntity<?> validateLogin(@RequestParam String username, @RequestParam String password) {
        if (!userService.validateLogin(username, password))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
