package org.ukma.spring.crooodle.entities;

import jakarta.persistence.*;
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
@Table(name = "cities")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 1, max = 500)
    @Column(nullable = false)
    private String name;

    @Embedded
    private CoordinatesEntity coordinates;

    @ManyToOne
    private CountryRegionEntity country_region;

    @ManyToOne(optional = false)
    private CountryEntity country;
}
