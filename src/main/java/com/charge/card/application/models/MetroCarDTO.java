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
public class MetroCarDTO {
    private Long id;
    private String number;
    private Boolean isActive;
    private Long passengerId;
}
