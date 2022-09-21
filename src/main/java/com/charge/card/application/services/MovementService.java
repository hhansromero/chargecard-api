package com.charge.card.application.services;

import com.charge.card.application.db.models.Card;
import com.charge.card.application.db.models.MetroCard;
import com.charge.card.application.db.models.Movement;
import com.charge.card.application.db.repositories.CardRepository;
import com.charge.card.application.db.repositories.MetroCardRepository;
import com.charge.card.application.db.repositories.MovementRepository;
import com.charge.card.application.models.CardDTO;
import com.charge.card.application.models.MetroCardDTO;
import com.charge.card.application.models.MovementDTO;
import com.charge.card.application.models.MovementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovementService {

    private final MovementRepository movementRepository;
    private final MetroCardRepository metroCardRepository;
    private final CardRepository cardRepository;

    @Autowired
    public MovementService(MovementRepository movementRepository, MetroCardRepository metroCardRepository,
                           CardRepository cardRepository) {
        this.movementRepository = movementRepository;
        this.metroCardRepository = metroCardRepository;
        this.cardRepository = cardRepository;
    }

    public Mono<MovementDTO> saveMovement(MovementDTO movementDTO) {
        MetroCard metroCard = metroCardRepository.findById(
                    movementDTO.getMetroCard().getId())
                .orElseThrow();

        Movement movement = Movement.builder()
                .recordedAt(LocalDateTime.now())
                .amount(movementDTO.getAmount())
                .movementType(MovementType.getMovementType(movementDTO.getMovementType()))
                .metroCardId(movementDTO.getMetroCard().getId())
                .cardId(movementDTO.getCard().getId())
                .build();

        movementRepository.save(movement);

        switch (movement.getMovementType()) {
            case CHARGE -> metroCard.setCurrentBalance(metroCard.getCurrentBalance() + movement.getAmount());
            case EXPENSE -> metroCard.setCurrentBalance(metroCard.getCurrentBalance() - movement.getAmount());
        }

        metroCardRepository.save(metroCard);

        return Mono.just(MovementDTO
                        .builder()
                        .id(movement.getId())
                        .recordedAt(movement.getRecordedAt().toString())
                        .amount(movement.getAmount())
                        .movementType(movement.getMovementType().getValue())
                        .metroCard(MetroCardDTO.builder()
                                .id(metroCard.getId())
                                .number(metroCard.getNumber())
                                .isActive(metroCard.getIsActive())
                                .currentBalance(metroCard.getCurrentBalance())
                                .passengerId(metroCard.getPassenger().getId())
                                .build())
                        .card(CardDTO.builder()
                                .id(movement.getCardId())
                                .build())
                        .build());
    }

    public Flux<MovementDTO> findMovementsByMetroCardId(Long metroCardId) {
        MetroCard metroCard = metroCardRepository.findById(metroCardId).orElseThrow();

        List<MovementDTO> movements = movementRepository.findByMetroCardId(metroCard.getId())
                .stream().map(m -> MovementDTO.builder()
                        .id(m.getId())
                        .recordedAt(m.getRecordedAt().toString())
                        .amount(m.getAmount())
                        .movementType(m.getMovementType().getValue())
                        .card(
                                buildCardDTOFromCardId(m.getCardId())
                        )
                        .metroCard(MetroCardDTO.builder()
                                .id(metroCard.getId())
                                .number(metroCard.getNumber())
                                .isActive(metroCard.getIsActive())
                                .currentBalance(metroCard.getCurrentBalance())
                                .passengerId(metroCard.getPassenger().getId())
                                .build())
                        .build())
                .collect(Collectors.toList());
        return Flux.fromStream(movements.stream());
    }

    private CardDTO buildCardDTOFromCardId(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow();
        return CardDTO.builder()
                .id(card.getId())
                .number(card.getNumber())
                .expiration(card.getExpiration())
                .cvv(card.getCvv())
                .cardHolder(card.getCardHolder())
                .currentBalance(card.getCurrentBalance())
                .passengerId(card.getPassenger().getId())
                .build();
    }

    private LocalDateTime buildLocalDateTime(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        return dateTime;
    }

}
