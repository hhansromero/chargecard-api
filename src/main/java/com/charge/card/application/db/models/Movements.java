package com.charge.card.application.db.models;

import com.charge.card.application.models.request.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movements {
    private Long id;
    private LocalDateTime recordedAt;
    private double amount;
    private MovementType movementType;
}
