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
public class MetroCardDTO {
    private Long id;
    private String number;
    private Boolean isActive;
    private double currentBalance;
    private Long passengerId;
}
