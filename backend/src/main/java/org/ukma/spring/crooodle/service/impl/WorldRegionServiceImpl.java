package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.mappers.WorldRegionMapper;
import org.ukma.spring.crooodle.repository.WorldRegionRepository;
import org.ukma.spring.crooodle.service.WorldRegionService;

@RequiredArgsConstructor
@Service
public class WorldRegionServiceImpl implements WorldRegionService {
    private final WorldRegionRepository repository;
    private final WorldRegionMapper mapper;

    @Override
    public int create(WorldRegionCreateRequestDto requestDto) {
        var entity = mapper.dtoToEntity(requestDto);
        repository.saveAndFlush(entity);

        return entity.getId();
    }

    @Override
    public WorldRegionAdminResponseDto getAdmin(int id) {
        var entity = repository.findById(id)
            .orElseThrow(PublicNotFoundException::new);

        return mapper.entityToAdminDto(entity);
    }

    @Override
    public void edit(int id, WorldRegionEditRequestDto requestDto) {
        var entity = repository.findById(id)
            .orElseThrow(PublicNotFoundException::new);

        mapper.update(entity, requestDto);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public PageResponseDto<WorldRegionAdminResponseDto> listAdmin(WorldRegionCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var entities = repository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<WorldRegionAdminResponseDto>builder()
            .items(entities.stream().map(mapper::entityToAdminDto).toList())
            .total(entities.getTotalElements())
            .build();
    }

    @Override
    public WorldRegionResponseDto get(int id) {
        var entity = repository.findById(id)
            .orElseThrow(PublicNotFoundException::new);

        return mapper.entityToDto(entity);
    }

    @Override
    public PageResponseDto<WorldRegionResponseDto> list(WorldRegionCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var entities = repository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<WorldRegionResponseDto>builder()
            .items(entities.stream().map(mapper::entityToDto).toList())
            .total(entities.getTotalElements())
            .build();
    }
}
