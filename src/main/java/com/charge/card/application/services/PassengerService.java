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
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

}
