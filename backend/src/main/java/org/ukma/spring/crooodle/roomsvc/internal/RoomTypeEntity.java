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
public class RoomTypeEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private HotelEntity hotel;

    @NotBlank
    @Column(nullable = false)
    private int price;
}
