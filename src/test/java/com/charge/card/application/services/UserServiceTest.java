package com.charge.card.application.services;

import com.charge.card.application.db.models.MetroCard;
import com.charge.card.application.db.models.Passenger;
import com.charge.card.application.db.models.User;
import com.charge.card.application.db.repositories.MetroCardRepository;
import com.charge.card.application.db.repositories.PassengerRepository;
import com.charge.card.application.db.repositories.UserRepository;
import com.charge.card.application.models.MetroCardDTO;
import com.charge.card.application.models.PassengerDTO;
import com.charge.card.application.models.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;
    @MockBean
    PassengerRepository passengerRepository;
    @MockBean
    MetroCardRepository metroCardRepository;
    @Autowired
    UserService userService;

    @Test
    void testFindUserById() throws ChangeSetPersister.NotFoundException {
        Long userId = 2000L;

        User user = User.builder().id(2000L).username("romero").password("romero").build();
        Passenger passenger = Passenger.builder().userId(1500L).name("Hector Romero").dni("12345678").email("hector.romero@gmail.com")
                .phone("999787111").useEmail(true).useSMS(true).build();
        MetroCard metroCard = MetroCard.builder().id(1200L).number("0007898212").isActive(true).currentBalance(99.00)
                .passenger(passenger).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(passengerRepository.findByUserId(userId)).thenReturn(Optional.of(passenger));

        when(metroCardRepository.findByPassenger(passenger)).thenReturn(Optional.of(metroCard));

        UserDTO userDTOResponse = userService.findUserById(userId).block();

        Assertions.assertEquals(user.getUsername(), userDTOResponse.getUsername());
        Assertions.assertEquals(user.getPassword(), userDTOResponse.getPassword());
        Assertions.assertEquals(passenger.getName(), userDTOResponse.getPassenger().getName());
        Assertions.assertEquals(passenger.getEmail(), userDTOResponse.getPassenger().getEmail());
        Assertions.assertEquals(metroCard.getNumber(), userDTOResponse.getMetroCard().getNumber());
        Assertions.assertEquals(metroCard.getIsActive(), userDTOResponse.getMetroCard().getIsActive());
    }

    @Test
    void testSaveUser() {
        User user = User.builder().id(2000L).username("romero").password("romero").build();
        Passenger passenger = Passenger.builder().userId(1500L).name("Hector Romero").dni("12345678").email("hector.romero@gmail.com")
                .phone("999787111").useEmail(true).useSMS(true).build();
        MetroCard metroCard = MetroCard.builder().id(1200L).number("0007898212").isActive(true).currentBalance(99.00)
                .passenger(passenger).build();
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .passenger(PassengerDTO.builder()
                        .id(passenger.getId())
                        .name(passenger.getName())
                        .email(passenger.getEmail())
                        .phone(passenger.getPhone())
                        .dni(passenger.getDni())
                        .useEmail(passenger.getUseEmail())
                        .useSMS(passenger.getUseSMS())
                        .build())
                .metroCard(MetroCardDTO.builder()
                        .id(metroCard.getId())
                        .number(metroCard.getNumber())
                        .isActive(metroCard.getIsActive())
                        .currentBalance(metroCard.getCurrentBalance())
                        .passengerId(metroCard.getPassenger().getId())
                        .build())
                .build();

        UserDTO userDTOResponse = userService.saveUser(userDTO).block();

        Assertions.assertEquals(userDTO.getUsername(), userDTOResponse.getUsername());
        Assertions.assertEquals(userDTO.getPassword(), userDTOResponse.getPassword());
        Assertions.assertEquals(userDTO.getPassenger().getName(), userDTOResponse.getPassenger().getName());
        Assertions.assertEquals(userDTO.getPassenger().getEmail(), userDTOResponse.getPassenger().getEmail());
        Assertions.assertEquals(userDTO.getMetroCard().getNumber(), userDTOResponse.getMetroCard().getNumber());
        Assertions.assertEquals(userDTO.getMetroCard().getIsActive(), userDTOResponse.getMetroCard().getIsActive());
    }

    @Test
    void testModifyUser() throws ChangeSetPersister.NotFoundException {
        Long userId = 2000L;

        User user = User.builder().id(2000L).username("romero").password("romero").build();
        Passenger passenger = Passenger.builder().userId(1500L).name("Hector Romero").dni("12345678").email("hector.romero@gmail.com")
                .phone("999787111").useEmail(true).useSMS(true).build();
        MetroCard metroCard = MetroCard.builder().id(1200L).number("0007898212").isActive(true).currentBalance(99.00)
                .passenger(passenger).build();
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .passenger(PassengerDTO.builder()
                        .id(passenger.getId())
                        .name(passenger.getName())
                        .email(passenger.getEmail())
                        .phone(passenger.getPhone())
                        .dni(passenger.getDni())
                        .useEmail(passenger.getUseEmail())
                        .useSMS(passenger.getUseSMS())
                        .build())
                .metroCard(MetroCardDTO.builder()
                        .id(metroCard.getId())
                        .number(metroCard.getNumber())
                        .isActive(metroCard.getIsActive())
                        .currentBalance(metroCard.getCurrentBalance())
                        .passengerId(metroCard.getPassenger().getId())
                        .build())
                .build();


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(passengerRepository.findByUserId(userId)).thenReturn(Optional.of(passenger));

        when(metroCardRepository.findByPassenger(passenger)).thenReturn(Optional.of(metroCard));

        UserDTO userDTOResponse = userService.modifyUser(userDTO, userId).block();

        Assertions.assertEquals(userDTO.getUsername(), userDTOResponse.getUsername());
        Assertions.assertEquals(userDTO.getPassword(), userDTOResponse.getPassword());
        Assertions.assertEquals(userDTO.getPassenger().getName(), userDTOResponse.getPassenger().getName());
        Assertions.assertEquals(userDTO.getPassenger().getEmail(), userDTOResponse.getPassenger().getEmail());
        Assertions.assertEquals(userDTO.getMetroCard().getNumber(), userDTOResponse.getMetroCard().getNumber());
        Assertions.assertEquals(userDTO.getMetroCard().getIsActive(), userDTOResponse.getMetroCard().getIsActive());
    }

}
