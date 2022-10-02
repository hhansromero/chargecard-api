package com.charge.card.application.services;

import com.charge.card.application.db.models.Card;
import com.charge.card.application.db.models.MetroCard;
import com.charge.card.application.db.models.Movement;
import com.charge.card.application.db.models.Passenger;
import com.charge.card.application.db.repositories.CardRepository;
import com.charge.card.application.db.repositories.MetroCardRepository;
import com.charge.card.application.db.repositories.MovementRepository;
import com.charge.card.application.models.CardDTO;
import com.charge.card.application.models.MetroCardDTO;
import com.charge.card.application.models.MovementDTO;
import com.charge.card.application.models.MovementType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MovementServiceTest {

    @MockBean
    MovementRepository movementRepository;
    @MockBean
    MetroCardRepository metroCardRepository;
    @MockBean
    CardRepository cardRepository;
    @Autowired
    MovementService movementService;

    @Test
    void testSaveMovementWithCHARGE() {
        MovementDTO movementDTO = MovementDTO.builder()
                .movementType("CHARGE")
                .recordedAt("2022-11-17 11:30:45")
                .amount(20.00)
                .card(CardDTO.builder().id(15900L).build())
                .metroCard(MetroCardDTO.builder().id(25900L).build())
                .build();

        MetroCard metroCard = MetroCard.builder()
                .id(25900L)
                .number("0009991070")
                .isActive(true)
                .currentBalance(100.00)
                .passenger(Passenger.builder()
                        .userId(1500L)
                        .name("Hector Romero")
                        .dni("12345678")
                        .email("hector.romero@gmail.com")
                        .phone("999787111")
                        .useEmail(true).useSMS(true)
                        .build())
                .build();

        when(metroCardRepository.findById(movementDTO.getMetroCard().getId()))
                .thenReturn(Optional.of(metroCard));

        MovementDTO movementDTOResponse = movementService.saveMovement(movementDTO).block();
        Assertions.assertEquals(movementDTO.getMovementType(), movementDTOResponse.getMovementType());
        Assertions.assertEquals(movementDTO.getAmount(), movementDTOResponse.getAmount());
        Assertions.assertEquals(metroCard.getId(), movementDTOResponse.getMetroCard().getId());
        Assertions.assertEquals(15900L, movementDTOResponse.getCard().getId());
    }

    @Test
    void testSaveMovementWithEXPENSE() {
        MovementDTO movementDTO = MovementDTO.builder()
                .movementType("EXPENSE")
                .recordedAt("2022-11-27 11:30:45")
                .amount(10)
                .card(CardDTO.builder().id(11900L).build())
                .metroCard(MetroCardDTO.builder().id(25900L).build())
                .build();

        MetroCard metroCard = MetroCard.builder()
                .id(25900L)
                .number("0009991033")
                .isActive(true)
                .currentBalance(100.00)
                .passenger(Passenger.builder()
                        .userId(1500L)
                        .name("Hans Romero")
                        .dni("12345678")
                        .email("hans.romero@gmail.com")
                        .phone("999787111")
                        .useEmail(true).useSMS(true)
                        .build())
                .build();

        when(metroCardRepository.findById(movementDTO.getMetroCard().getId()))
                .thenReturn(Optional.of(metroCard));

        MovementDTO movementDTOResponse = movementService.saveMovement(movementDTO).block();
        Assertions.assertEquals(movementDTO.getMovementType(), movementDTOResponse.getMovementType());
        Assertions.assertEquals(movementDTO.getAmount(), movementDTOResponse.getAmount());
        Assertions.assertEquals(metroCard.getId(), movementDTOResponse.getMetroCard().getId());
        Assertions.assertEquals(11900L, movementDTOResponse.getCard().getId());
    }

    @Test
    void testFindMovementsByMetroCardId() {
        Long metroCardId = 11000L;

        MetroCard metroCard = MetroCard.builder()
                .id(11000L)
                .number("0009991155")
                .isActive(true)
                .currentBalance(25)
                .passenger(Passenger.builder()
                        .userId(1500L)
                        .name("Hans Romero")
                        .dni("87654321")
                        .email("hans.romero@test.com")
                        .phone("999782000")
                        .useEmail(true).useSMS(true)
                        .build())
                .build();

        Movement movement = Movement.builder().id(1200L).amount(10.00).movementType(MovementType.CHARGE).cardId(5L)
                .metroCardId(11000L).recordedAt(LocalDateTime.now()).build();
        List<Movement> movements = Arrays.asList(movement);

        Card card = Card.builder()
                .id(5L)
                .number("453390909090")
                .expiration("07/24")
                .cvv("322")
                .cardHolder("Hector Romero")
                .passenger(Passenger.builder().dni("44040109").build())
                .currentBalance(400.00)
                .build();

        when(metroCardRepository.findById(metroCardId)).thenReturn(Optional.of(metroCard));

        when(movementRepository.findByMetroCardId(metroCardId)).thenReturn(movements);

        when(cardRepository.findById(5L)).thenReturn(Optional.of(card));

        Assertions.assertDoesNotThrow(() -> movementService.findMovementsByMetroCardId(metroCardId));
        verify(metroCardRepository).findById(metroCardId);
        verify(movementRepository).findByMetroCardId(metroCardId);
        verify(cardRepository).findById(5L);
    }
}
