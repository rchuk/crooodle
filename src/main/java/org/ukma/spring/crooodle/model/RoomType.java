package org.ukma.spring.crooodle.model;

import jakarta.persistence.*;
import lombok.*;
import org.ukma.spring.crooodle.model.enums.RoomTypeKind;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomTypeKind type; // e.g., "Standard", "Deluxe"
}
