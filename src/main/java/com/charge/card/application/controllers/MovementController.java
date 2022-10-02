package com.charge.card.application.controllers;

import com.charge.card.application.models.MovementDTO;
import com.charge.card.application.services.MovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class MovementController {

    Logger logger = LoggerFactory.getLogger(MovementController.class);
    private final MovementService movementService;

    @Autowired
    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping("/movements")
    public Flux<MovementDTO> getMovementsByMetroCardId(@RequestParam Long metroCardId) {
        logger.info("Received param - metroCardId: {}.", metroCardId);
        return movementService.findMovementsByMetroCardId(metroCardId);
    }

    @PostMapping("/movements")
    public Mono<MovementDTO> postMovement(@RequestBody MovementDTO movementDTO) {
        try {
            logger.info("Received payload: {}.", movementDTO.toString());
            return movementService.saveMovement(movementDTO);
        } catch (Exception e) {
            logger.error("An exception occurred, e: {}.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error creando el movimiento.", e);
        }
    }
}
