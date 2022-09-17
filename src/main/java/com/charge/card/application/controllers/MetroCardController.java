package com.charge.card.application.controllers;

import com.charge.card.application.models.MetroCarDTO;
import com.charge.card.application.services.MetroCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class MetroCardController {

    private final MetroCardService metroCardService;

    @Autowired
    public MetroCardController(MetroCardService metroCardService) {
        this.metroCardService = metroCardService;
    }

    @PostMapping("/metro-cards")
    public Mono<MetroCarDTO> postMetroCard(@RequestBody MetroCarDTO metroCarDTO) {
        return metroCardService.saveMetroCard(metroCarDTO);
    }

}
