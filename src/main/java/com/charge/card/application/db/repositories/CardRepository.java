package com.charge.card.application.db.repositories;

import com.charge.card.application.db.models.Card;
import com.charge.card.application.db.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByPassenger(Passenger passenger);
}
