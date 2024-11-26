package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.domain.Specification;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.entities.CountryEntity;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryEntity dtoToEntity(CountryCreateRequestDto requestDto);
    void update(@MappingTarget CountryEntity entity, CountryEditRequestDto requestDto);

    CountryResponseDto entityToDto(CountryEntity entity);
    CountryAdminResponseDto entityToAdminDto(CountryEntity entity);

    default Specification<CountryEntity> criteriaToSpec(CountryCriteriaDto criteriaDto) {
        return (root, _, builder) -> criteriaDto.getQuery() != null
            ? builder.like(builder.lower(root.get("name")), "%" + criteriaDto.getQuery().toLowerCase() + "%")
            : builder.conjunction();
    }
}
