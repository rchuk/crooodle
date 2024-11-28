package org.ukma.spring.crooodle.dto;

import lombok.*;
import org.ukma.spring.crooodle.entities.CountryEntity;
import org.ukma.spring.crooodle.entities.CountryRegionEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityDTO {

    // Getter та setter для id, якщо їх немає
    @Setter
    @Getter
    private Integer id;  // Додано поле id для підтримки оновлення
    private String name;
    private CountryEntity country;
    private CountryRegionEntity countryRegion;

}
