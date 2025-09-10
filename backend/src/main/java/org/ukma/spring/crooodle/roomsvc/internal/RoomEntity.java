package org.ukma.spring.crooodle.roomsvc.internal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;

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
    private RoomTypeEntity type;

    @ManyToOne
    private HotelEntity hotel;


    @NotBlank
    @Column(nullable = false)
    private String number;

    // will be removed soon...
    @Column(nullable = false)
    private boolean isOccupied;

}
