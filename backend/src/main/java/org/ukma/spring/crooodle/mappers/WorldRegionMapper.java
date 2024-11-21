package org.ukma.spring.crooodle.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.ukma.spring.crooodle.dto.WorldRegionAdminResponseDto;
import org.ukma.spring.crooodle.dto.WorldRegionCreateRequestDto;
import org.ukma.spring.crooodle.dto.WorldRegionEditRequestDto;
import org.ukma.spring.crooodle.dto.WorldRegionResponseDto;
import org.ukma.spring.crooodle.entities.WorldRegionEntity;
import org.ukma.spring.crooodle.service.CountryService;

@Mapper(componentModel = "spring", uses = { CountryService.class })
public interface WorldRegionMapper {
    WorldRegionEntity dtoToEntity(WorldRegionCreateRequestDto requestDto);
    void update(@MappingTarget WorldRegionEntity entity, WorldRegionEditRequestDto requestDto);

    WorldRegionResponseDto entityToDto(WorldRegionEntity entity);
    WorldRegionAdminResponseDto entityToAdminDto(WorldRegionEntity entity);


}
