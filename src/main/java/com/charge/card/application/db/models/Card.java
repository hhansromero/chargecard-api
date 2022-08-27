package com.charge.card.application.db.models;

import lombok.*;

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
