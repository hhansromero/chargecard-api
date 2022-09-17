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
public class PassengerDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String dni;
    private Boolean useSMS;
    private Boolean useEmail;
}
