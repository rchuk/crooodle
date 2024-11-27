package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.mappers.HotelMapper;
import org.ukma.spring.crooodle.repository.HotelRepository;
import org.ukma.spring.crooodle.service.HotelService;

@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository repository;
    private final HotelMapper mapper;

    @Override
    public long create(HotelCreateRequestDto requestDto) {
        var entity = mapper.dtoToEntity(requestDto);
        repository.saveAndFlush(entity);

        return entity.getId();
    }

    @Override
    public HotelAdminResponseDto getAdmin(long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        return mapper.entityToAdminDto(entity);
    }

    @Override
    public HotelResponseDto get(long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        return mapper.entityToDto(entity);
    }

    @Override
    public PageResponseDto<HotelAdminResponseDto> listAdmin(HotelCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var entities = repository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<HotelAdminResponseDto>builder()
            .items(entities.stream().map(mapper::entityToAdminDto).toList())
            .total(entities.getTotalElements())
            .build();
    }

    @Override
    public PageResponseDto<HotelResponseDto> list(HotelCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var entities = repository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<HotelResponseDto>builder()
            .items(entities.stream().map(mapper::entityToDto).toList())
            .total(entities.getTotalElements())
            .build();
    }

    @Override
    public void edit(long id, HotelEditRequestDto requestDto) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        mapper.update(entity, requestDto);

        repository.save(entity);
    }

    @Override
    public void delete(long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        repository.delete(entity);
    }
}
