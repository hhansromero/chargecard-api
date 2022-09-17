package com.charge.card.application.services;

import com.charge.card.application.db.models.MetroCard;
import com.charge.card.application.db.models.Passenger;
import com.charge.card.application.db.repositories.MetroCardRepository;
import com.charge.card.application.db.repositories.PassengerRepository;
import com.charge.card.application.models.MetroCarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MetroCardService {

    private final MetroCardRepository metroCardRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public MetroCardService(MetroCardRepository metroCardRepository, PassengerRepository passengerRepository) {
        this.metroCardRepository = metroCardRepository;
        this.passengerRepository = passengerRepository;
    }

    public Mono<MetroCarDTO> saveMetroCard(MetroCarDTO metroCarDTO) {
        Passenger passenger = passengerRepository.findById(metroCarDTO.getPassengerId()).orElseThrow();

        MetroCard metroCard = MetroCard.builder()
                .number(metroCarDTO.getNumber())
                .isActive(metroCarDTO.getIsActive())
                .passenger(passenger)
                .build();

        metroCardRepository.save(metroCard);

        return Mono.just(
                MetroCarDTO.builder()
                    .id(metroCard.getId())
                    .number(metroCard.getNumber())
                    .isActive(metroCard.getIsActive())
                    .passengerId(passenger.getId())
                .build());
    }
}
