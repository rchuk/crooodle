package org.ukma.spring.crooodle.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "countries")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 1, max = 500)
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<CityEntity> cities;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<CountryRegionEntity> regions;

    @ManyToOne
    private WorldRegionEntity worldRegion;
}
