package com.charge.card.application.services;

import com.charge.card.application.db.models.Card;
import com.charge.card.application.db.models.Passenger;
import com.charge.card.application.db.repositories.CardRepository;
import com.charge.card.application.db.repositories.PassengerRepository;
import com.charge.card.application.models.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public CardService(CardRepository cardRepository, PassengerRepository passengerRepository) {
        this.cardRepository = cardRepository;
        this.passengerRepository = passengerRepository;
    }

    public Mono<CarDTO> saveCard(CarDTO carDTO) {
        Passenger passenger = passengerRepository.findById(carDTO.getPassengerId()).orElseThrow();

        Card card = Card.builder()
                .number(carDTO.getNumber())
                .expiration(carDTO.getExpiration())
                .cvv(carDTO.getCvv())
                .cardHolder(carDTO.getCardHolder())
                .passenger(passenger)
                .currentBalance(200.00) // amount by default
                .build();

        cardRepository.save(card);

        return Mono.just(
                CarDTO.builder()
                    .id(card.getId())
                    .number(card.getNumber())
                    .expiration(card.getExpiration())
                    .cvv(card.getCvv())
                    .cardHolder(card.getCardHolder())
                    .passengerId(passenger.getId())
                .build());
    }

    public Flux<CarDTO> findCardsByPassengerId(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();

        List<CarDTO> cards = cardRepository.findByPassenger(passenger)
                .stream().map(c -> CarDTO.builder()
                        .id(c.getId())
                        .number(c.getNumber())
                        .expiration(c.getExpiration())
                        .cvv(c.getCvv())
                        .cardHolder(c.getCardHolder())
                        .passengerId(c.getPassenger().getId())
                        .build())
                .collect(Collectors.toList());
        return Flux.fromStream(cards.stream());
    }
}
