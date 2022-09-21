package com.charge.card.application.controllers;

import com.charge.card.application.models.UserDTO;
import com.charge.card.application.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserDTO> validateLogin(@RequestBody UserDTO userRequest) {
        try {
            logger.info("Received payload: {}.", userRequest);
            return ResponseEntity.ok(
                    userService.loginUser(userRequest.getUsername(), userRequest.getPassword()));
        } catch (Exception e) {
            logger.error("An exception occurred, e: {}.", e);
            return ResponseEntity.notFound().build();
        }
    }
}
