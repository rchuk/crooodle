package org.ukma.spring.crooodle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ukma.spring.crooodle.entities.CountryEntity;
import org.ukma.spring.crooodle.entities.CountryRegionEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityAdminEditRequestDto {
    private String name;
    private CountryEntity country;
    private CountryRegionEntity countryRegion;
}
