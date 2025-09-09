package org.ukma.spring.crooodle.roomsvc.internal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.reservationsvc.ReservationState;
import org.ukma.spring.crooodle.roomsvc.RoomType;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntity {


    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "id",
            foreignKey = @ForeignKey(name = "fk_room_type")
    )
    private RoomType type;


    @NotBlank
    @Column(nullable = false)
    private String number;

}
