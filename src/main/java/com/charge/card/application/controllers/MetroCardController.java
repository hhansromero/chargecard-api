package com.charge.card.application.controllers;

import com.charge.card.application.models.MetroCardDTO;
import com.charge.card.application.services.MetroCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class MetroCardController {

    Logger logger = LoggerFactory.getLogger(MetroCardController.class);
    private final MetroCardService metroCardService;

    @Autowired
    public MetroCardController(MetroCardService metroCardService) {
        this.metroCardService = metroCardService;
    }

    @PostMapping("/metro-cards")
    public Mono<MetroCardDTO> postMetroCard(@RequestBody MetroCardDTO metroCardDTO) {
        logger.info("Received payload: {}.", metroCardDTO);
        return metroCardService.saveMetroCard(metroCardDTO);
    }

}
