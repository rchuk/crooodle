package org.ukma.spring.crooodle.reservationsvc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private UUID roomId;
    @NotNull
    private UUID profileId;

    @NotNull
    private Date checkInDate;
    @NotNull
    private Date checkOutDate;
}
