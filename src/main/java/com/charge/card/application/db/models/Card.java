package com.charge.card.application.db.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private Long id;
    private String number;
    private String expiration;
    private String cvv;
}
