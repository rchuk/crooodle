package org.ukma.spring.crooodle.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.entities.CountryEntity;
import org.ukma.spring.crooodle.entities.WorldRegionEntity;
import org.ukma.spring.crooodle.repository.WorldRegionRepository;

@Component
@Mapper(componentModel = "spring")
public abstract class CountryMapper {
    @Autowired
    private WorldRegionRepository worldRegionRepository;

    @Mapping(source = "worldRegionId", target = "worldRegion", qualifiedByName = "mapWorldRegionIdToEntity")
    public abstract CountryEntity dtoToEntity(CountryCreateRequestDto requestDto);
    public abstract void update(@MappingTarget CountryEntity entity, CountryEditRequestDto requestDto);

    public abstract CountryResponseDto entityToDto(CountryEntity entity);
    public abstract CountryAdminResponseDto entityToAdminDto(CountryEntity entity);

    public Specification<CountryEntity> criteriaToSpec(CountryCriteriaDto criteriaDto) {
        return (root, _, builder) -> criteriaDto.getQuery() != null
            ? builder.like(builder.lower(root.get("name")), "%" + criteriaDto.getQuery().toLowerCase() + "%")
            : builder.conjunction();
    }

    @Named("mapWorldRegionIdToEntity")
    public WorldRegionEntity mapWorldRegionIdToEntity(Integer worldRegionId) {
        return worldRegionId != null ? worldRegionRepository.findById(worldRegionId).orElse(null) : null;
    }
}
