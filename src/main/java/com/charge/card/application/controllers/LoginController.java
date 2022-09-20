package com.charge.card.application.controllers;

import com.charge.card.application.models.UserDTO;
import com.charge.card.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> validateLogin(@RequestBody UserDTO userRequest) {
        if (!userService.validateLogin(userRequest.getUsername(), userRequest.getPassword()))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
