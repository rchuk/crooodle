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
@Table(
    name = "country_regions",
    uniqueConstraints = { @UniqueConstraint(columnNames = { "country_id", "name" }) }
)
public class CountryRegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 1, max = 500)
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "country_region", cascade = CascadeType.PERSIST)
    private List<CityEntity> cities;

    @ManyToOne(optional = false)
    private CountryEntity country;
}
