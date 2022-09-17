package com.charge.card.application.services;

import com.charge.card.application.db.models.Passenger;
import com.charge.card.application.db.models.User;
import com.charge.card.application.db.repositories.PassengerRepository;
import com.charge.card.application.db.repositories.UserRepository;
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

    @Autowired
    public UserService(UserRepository userRepository, PassengerRepository passengerRepository) {
        this.userRepository = userRepository;
        this.passengerRepository = passengerRepository;
    }

    public Mono<UserDTO> saveUser(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
        User userCreated = userRepository.save(user);

        Passenger passenger = Passenger.builder()
                .name(userDTO.getPassenger().getName())
                .email(userDTO.getPassenger().getEmail())
                .phone(userDTO.getPassenger().getPhone())
                .dni(userDTO.getPassenger().getDni())
                .useEmail(userDTO.getPassenger().getUseEmail())
                .useSMS(userDTO.getPassenger().getUseSMS())
                .userId(userCreated.getId())
                .build();

        Passenger passengerCreated = passengerRepository.save(passenger);

        return Mono.just(
                UserDTO.builder()
                .id(userCreated.getId())
                .username(userCreated.getUsername())
                .password("SECRET")
                .passenger(PassengerDTO.builder()
                        .id(passengerCreated.getId())
                        .name(passengerCreated.getName())
                        .email(passengerCreated.getEmail())
                        .phone(passengerCreated.getPhone())
                        .dni(passengerCreated.getDni())
                        .useEmail(passengerCreated.getUseEmail())
                        .useSMS(passengerCreated.getUseSMS())
                        .build())
                .build());
    }

    public Mono<UserDTO> modifyUser(UserDTO userDTO, Long userId)
            throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        Passenger passenger = passengerRepository.findByUserId(userId)
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

        return Mono.just(
                UserDTO.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password("SECRET")
                    .passenger(PassengerDTO.builder()
                        .id(passenger.getId())
                        .name(passenger.getName())
                        .email(passenger.getEmail())
                        .phone(passenger.getPhone())
                        .dni(passenger.getDni())
                        .useEmail(passenger.getUseEmail())
                        .useSMS(passenger.getUseSMS())
                        .build())
                .build());
    }
}
