package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.domain.Specification;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.entities.WorldRegionEntity;
import org.ukma.spring.crooodle.service.CountryService;

@Mapper(componentModel = "spring", uses = { CountryService.class })
public interface WorldRegionMapper {
    WorldRegionEntity dtoToEntity(WorldRegionCreateRequestDto requestDto);
    void update(@MappingTarget WorldRegionEntity entity, WorldRegionEditRequestDto requestDto);

    @Mapping(target = "countryCount", expression = "java(entity.getCountries().size())")
    WorldRegionResponseDto entityToDto(WorldRegionEntity entity);
    WorldRegionAdminResponseDto entityToAdminDto(WorldRegionEntity entity);

    default Specification<WorldRegionEntity> criteriaToSpec(WorldRegionCriteriaDto criteriaDto) {
        return (root, r1, builder) -> criteriaDto.getQuery() != null
            ? builder.like(builder.lower(root.get("name")), "%" + criteriaDto.getQuery().toLowerCase() + "%")
            : builder.conjunction();
    }
}
