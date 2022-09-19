package com.charge.card.application.db.repositories;

import com.charge.card.application.db.models.MetroCard;
import com.charge.card.application.db.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetroCardRepository extends JpaRepository<MetroCard, Long> {
    Optional<MetroCard> findByPassenger(Passenger passenger);
}
