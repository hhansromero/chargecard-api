package com.charge.card.application.services;

import com.charge.card.application.db.models.Card;
import com.charge.card.application.db.models.Passenger;
import com.charge.card.application.db.repositories.CardRepository;
import com.charge.card.application.db.repositories.PassengerRepository;
import com.charge.card.application.models.CardDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CardServiceTest {

    @MockBean
    CardRepository cardRepository;
    @MockBean
    PassengerRepository passengerRepository;
    @Autowired
    CardService cardService;

    @Test
    void testSaveCard() {
        CardDTO cardDTO = CardDTO.builder()
                .number("454590901212")
                .expiration("09/25")
                .cvv("344")
                .cardHolder("Hector Romero")
                .passengerId(1500L)
                .build();

        Passenger passenger = Passenger.builder()
                .userId(1500L)
                .name("Hector Romero")
                .dni("12345678")
                .email("hector.romero@gmail.com")
                .phone("999787111")
                .useEmail(true)
                .useSMS(true)
                .build();

        when(passengerRepository.findById(cardDTO.getPassengerId()))
                .thenReturn(Optional.of(passenger));

        CardDTO cardDTOResponse = cardService.saveCard(cardDTO).block();

        Assertions.assertEquals(cardDTO.getNumber(), cardDTOResponse.getNumber());
        Assertions.assertEquals(cardDTO.getExpiration(), cardDTOResponse.getExpiration());
        Assertions.assertEquals(cardDTO.getCvv(), cardDTOResponse.getCvv());
        Assertions.assertEquals(cardDTO.getCardHolder(), cardDTOResponse.getCardHolder());
    }

    @Test
    void testFindCardsByPassengerId() {
        Long passengerId = 1500L;

        Card card1 = Card.builder()
                .number("454590901111")
                .expiration("09/23")
                .cvv("344")
                .cardHolder("Hector Romero")
                .passenger(Passenger.builder().dni("44040109").build())
                .currentBalance(300.00)
                .build();
        Card card2 = Card.builder()
                .number("454590902222")
                .expiration("09/24")
                .cvv("322")
                .cardHolder("Hector Romero")
                .passenger(Passenger.builder().dni("44040109").build())
                .currentBalance(400.00)
                .build();

        List<Card> cards = Arrays.asList(card1, card2);

        Passenger passenger = Passenger.builder()
                .userId(1500L)
                .name("Hector Romero")
                .dni("12345678")
                .email("hector.romero@gmail.com")
                .phone("999787111")
                .useEmail(true)
                .useSMS(true)
                .build();

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));

        when(cardRepository.findByPassenger(passenger)).thenReturn(cards);

        Assertions.assertDoesNotThrow(() -> cardService.findCardsByPassengerId(passengerId));
        verify(passengerRepository).findById(passengerId);
        verify(cardRepository).findByPassenger(passenger);
    }
}
