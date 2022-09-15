package com.charge.card.application.db.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "use_sms")
    private Boolean useSMS;
    @Column(name = "use_email")
    private Boolean useEmail;
    private String name;
    private String email;
    private String phone;
    private String dni;

    @OneToMany(
            mappedBy = "passenger",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Card> cards;

    @OneToMany(
            mappedBy = "passenger",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<MetroCard> metroCards;
}
