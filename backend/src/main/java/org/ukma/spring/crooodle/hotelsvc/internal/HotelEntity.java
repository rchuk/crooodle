package org.ukma.spring.crooodle.hotelsvc.internal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private UUID ownerId;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String address;
}
