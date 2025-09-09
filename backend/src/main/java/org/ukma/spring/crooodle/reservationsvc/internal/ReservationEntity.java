package org.ukma.spring.crooodle.reservationsvc.internal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.ukma.spring.crooodle.reservationsvc.ReservationState;
import org.ukma.spring.crooodle.roomsvc.internal.RoomEntity;

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
    @JoinColumn(
            name = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_room_reservation")
    )
    private RoomEntity room;

    @NotBlank
    @Column(nullable = false)
    private int price;

    @NotBlank
    @Column(nullable = false)
    private Date checkIn;

    @NotBlank
    @Column(nullable = false)
    private Date checkOut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationState state;
}
