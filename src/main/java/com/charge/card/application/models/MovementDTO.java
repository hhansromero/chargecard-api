package com.charge.card.application.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementDTO {
    private Long id;
    private String recordedAt;
    private double amount;
    private String movementType;
    private MetroCardDTO metroCard;
    private CardDTO card;
}
