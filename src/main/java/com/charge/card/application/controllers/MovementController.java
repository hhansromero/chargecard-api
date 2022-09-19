package com.charge.card.application.controllers;

import com.charge.card.application.models.MovementDTO;
import com.charge.card.application.services.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class MovementController {

    private final MovementService movementService;

    @Autowired
    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping("/movements")
    public Flux<MovementDTO> getMovementsByMetroCardId(@RequestParam Long metroCardId) {
        return movementService.findMovementsByMetroCardId(metroCardId);
    }

    @PostMapping("/movements")
    public Mono<MovementDTO> postMovement(@RequestBody MovementDTO movementDTO) {
        return movementService.saveMovement(movementDTO);
    }

}
