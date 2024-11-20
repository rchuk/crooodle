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
@Table(name = "world_regions")
public class WorldRegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 1, max = 500)
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "worldRegion", cascade = CascadeType.PERSIST)
    private List<CountryEntity> countries;
}
