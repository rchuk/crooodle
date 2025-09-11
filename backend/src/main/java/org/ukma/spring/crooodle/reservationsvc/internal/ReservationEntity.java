package org.ukma.spring.crooodle.reservationsvc.internal;

import jakarta.persistence.*;
import lombok.*;
import org.ukma.spring.crooodle.reservationsvc.ReservationState;
import org.ukma.spring.crooodle.roomsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.usersvc.internal.UserEntity;

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

    @ManyToOne
    private RoomEntity room;

    @ManyToOne
    private UserEntity user;

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
