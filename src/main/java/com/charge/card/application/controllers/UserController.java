package com.charge.card.application.controllers;

import com.charge.card.application.models.UserDTO;
import com.charge.card.application.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public Mono<UserDTO> getUser(@PathVariable Long userId) {
        try {
            logger.info("Received param - userId: {}.", userId);
            return userService.findUserById(userId);
        } catch (ChangeSetPersister.NotFoundException e) {
            logger.error("An exception occurred, e: {}.", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario.", e);
        }
    }

    @PostMapping("/users")
    public Mono<UserDTO> postUser(@RequestBody UserDTO userDTO) {
        try {
            logger.info("Received payload: {}.", userDTO.toString());
            return userService.saveUser(userDTO);
        } catch (Exception e) {
            logger.error("An exception occurred, e: {}.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error creando el usuario.", e);
        }
    }

    @PutMapping("/users/{userId}")
    public Mono<UserDTO> putUser(@RequestBody UserDTO userDTO, @PathVariable Long userId) {
        try {
            logger.info("Received param - userId: {}.", userId);
            return userService.modifyUser(userDTO, userId);
        } catch (ChangeSetPersister.NotFoundException e) {
            logger.error("An exception occurred, e: {}.", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario.", e);
        }
    }
}
