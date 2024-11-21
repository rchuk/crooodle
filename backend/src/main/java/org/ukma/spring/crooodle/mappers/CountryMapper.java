package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.ukma.spring.crooodle.dto.CountryAdminResponseDto;
import org.ukma.spring.crooodle.dto.CountryCreateRequestDto;
import org.ukma.spring.crooodle.dto.CountryEditRequestDto;
import org.ukma.spring.crooodle.dto.CountryResponseDto;
import org.ukma.spring.crooodle.entities.CountryEntity;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryEntity dtoToEntity(CountryCreateRequestDto requestDto);
    void update(@MappingTarget CountryEntity entity, CountryEditRequestDto requestDto);

    CountryResponseDto entityToDto(CountryEntity entity);
    CountryAdminResponseDto entityToAdminDto(CountryEntity entity);
}
