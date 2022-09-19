package com.charge.card.application.db.repositories;

import com.charge.card.application.db.models.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findByMetroCardId(Long metroCardId);
}
