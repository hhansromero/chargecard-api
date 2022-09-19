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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;
    private final MetroCardRepository metroCardRepository;

    @Autowired
    public UserService(UserRepository userRepository, PassengerRepository passengerRepository,
                       MetroCardRepository metroCardRepository) {
        this.userRepository = userRepository;
        this.passengerRepository = passengerRepository;
        this.metroCardRepository = metroCardRepository;
    }

    public Mono<UserDTO> findUserById(Long userId) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        Passenger passenger = passengerRepository.findByUserId(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        MetroCard metroCard = metroCardRepository.findByPassenger(passenger)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        return buildUserDTO(user, passenger, metroCard);
    }

    public Mono<UserDTO> saveUser(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
        userRepository.save(user);

        Passenger passenger = Passenger.builder()
                .name(userDTO.getPassenger().getName())
                .email(userDTO.getPassenger().getEmail())
                .phone(userDTO.getPassenger().getPhone())
                .dni(userDTO.getPassenger().getDni())
                .useEmail(userDTO.getPassenger().getUseEmail())
                .useSMS(userDTO.getPassenger().getUseSMS())
                .userId(user.getId())
                .build();
        passengerRepository.save(passenger);

        MetroCard metroCard = MetroCard.builder()
                .number(userDTO.getMetroCard().getNumber())
                .isActive(userDTO.getMetroCard().getIsActive())
                .currentBalance(0.00)
                .passenger(passenger)
                .build();
        metroCardRepository.save(metroCard);

        return buildUserDTO(user, passenger, metroCard);
    }

    public Mono<UserDTO> modifyUser(UserDTO userDTO, Long userId) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        Passenger passenger = passengerRepository.findByUserId(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        MetroCard metroCard = metroCardRepository.findByPassenger(passenger)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);

        passenger.setName(userDTO.getPassenger().getName());
        passenger.setEmail(userDTO.getPassenger().getEmail());
        passenger.setPhone(userDTO.getPassenger().getPhone());
        passenger.setDni(userDTO.getPassenger().getDni());
        passenger.setUseEmail(userDTO.getPassenger().getUseEmail());
        passenger.setUseSMS(userDTO.getPassenger().getUseSMS());
        passengerRepository.save(passenger);

        metroCard.setNumber(userDTO.getMetroCard().getNumber());
        metroCard.setIsActive(userDTO.getMetroCard().getIsActive());
        metroCard.setCurrentBalance(userDTO.getMetroCard().getCurrentBalance());
        metroCard.setPassenger(passenger);
        metroCardRepository.save(metroCard);

        return buildUserDTO(user, passenger, metroCard);
    }

    private Mono<UserDTO> buildUserDTO(User user, Passenger passenger, MetroCard metroCard) {
        return  Mono.just(
                UserDTO.builder()
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
                        .build());
    }
}
