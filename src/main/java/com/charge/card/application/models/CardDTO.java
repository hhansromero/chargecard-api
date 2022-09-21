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
public class CardDTO {
    private Long id;
    private String number;
    private String expiration;
    private String cvv;
    private String cardHolder;
    private double currentBalance;
    private Long passengerId;
}
