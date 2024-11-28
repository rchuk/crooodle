package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.domain.Specification;
import org.ukma.spring.crooodle.dto.CityDTO;
import org.ukma.spring.crooodle.entities.CityEntity;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityEntity dtoToEntity(CityDTO cityDTO);  // Перевірте правильність типів даних

    void update(@MappingTarget CityEntity entity, CityDTO cityDTO);

    CityDTO entityToDto(CityEntity cityEntity);

    default Specification<CityEntity> criteriaToSpec(CityDTO criteriaDto) {
        return (root, r1, builder) -> {
            var query = criteriaDto.getName() != null
                ? builder.like(builder.lower(root.get("name")), "%" + criteriaDto.getName().toLowerCase() + "%")
                : builder.conjunction();
            var countryId = criteriaDto.getCountry() != null
                ? builder.equal(root.get("country"), criteriaDto.getCountry())
                : builder.conjunction();
            var regionId = criteriaDto.getCountryRegion() != null
                ? builder.equal(root.get("country_region"), criteriaDto.getCountryRegion())
                : builder.conjunction();

            return builder.and(query, countryId, regionId);
        };
    }
}
