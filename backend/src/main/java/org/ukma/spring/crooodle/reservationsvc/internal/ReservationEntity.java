package org.ukma.spring.crooodle.reservationsvc.internal;

import jakarta.persistence.*;
import lombok.*;
import org.ukma.spring.crooodle.reservationsvc.ReservationState;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID roomId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Date checkin;

    @Column(nullable = false)
    private Date checkout;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationState state;
}
