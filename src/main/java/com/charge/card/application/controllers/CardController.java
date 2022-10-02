package com.charge.card.application.controllers;

import com.charge.card.application.models.CardDTO;
import com.charge.card.application.services.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class CardController {

    Logger logger = LoggerFactory.getLogger(CardController.class);

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/cards")
    public Mono<CardDTO> postCard(@RequestBody CardDTO cardDTO) {
        try {
            logger.info("Received payload: {}.", cardDTO.toString());
            return cardService.saveCard(cardDTO);
        } catch (Exception e) {
            logger.error("An exception occurred, e: {}.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error creando la tarjeta.", e);
        }
    }

    @GetMapping("/cards/by-passenger/{passengerId}")
    public Flux<CardDTO> getCardsByPassengerId(@PathVariable Long passengerId) {
        logger.info("Received param - passengerId: {}.", passengerId);
        return cardService.findCardsByPassengerId(passengerId);
    }

}
