package org.ukma.spring.crooodle.roomsvc.internal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
    private RoomTypeEntity type;


    @NotBlank
    @Column(nullable = false)
    private String number;

    @NotBlank
    @Column(nullable = false)
    private boolean isOccupied;

}
