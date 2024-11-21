package org.ukma.spring.crooodle.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.ukma.spring.crooodle.entities.embedded.CoordinatesEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "hotels")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 1, max = 500)
    @Column(nullable = false)
    private String name;
    @Length(min = 1, max = 500)
    @Column(nullable = false)
    private String address;

    @PositiveOrZero
    private long rankSum;
    @PositiveOrZero
    private int rankCount;

    @Embedded
    private CoordinatesEntity coordinates;

    @ManyToOne
    private CountryRegionEntity country_region;

    @ManyToOne(optional = false)
    private CountryEntity country;
}
