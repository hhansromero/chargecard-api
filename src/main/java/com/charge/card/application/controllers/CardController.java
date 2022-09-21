package com.charge.card.application.controllers;

import com.charge.card.application.models.CardDTO;
import com.charge.card.application.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/cards")
    public Mono<CardDTO> postCard(@RequestBody CardDTO cardDTO) {
        return cardService.saveCard(cardDTO);
    }

    @GetMapping("/cards/by-passenger/{passengerId}")
    public Flux<CardDTO> getCardsByPassengerId(@PathVariable Long passengerId) {
        return cardService.findCardsByPassengerId(passengerId);
    }

}
