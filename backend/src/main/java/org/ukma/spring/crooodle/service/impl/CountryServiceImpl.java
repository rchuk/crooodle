package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.entities.CountryEntity;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.mappers.CountryMapper;
import org.ukma.spring.crooodle.repository.CountryRepository;
import org.ukma.spring.crooodle.service.CountryService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;
    private final CountryMapper mapper;

    @Override
    public int create(CountryCreateRequestDto requestDto) {
        var entity = mapper.dtoToEntity(requestDto);
        repository.saveAndFlush(entity);

        return entity.getId();
    }

    @Override
    public CountryAdminResponseDto getAdmin(int id) {
        var entity = repository.findById(id)
            .orElseThrow(PublicNotFoundException::new);

        return mapper.entityToAdminDto(entity);
    }

    @Override
    public void edit(int id, CountryEditRequestDto requestDto) {
        var entity = repository.findById(id)
            .orElseThrow(PublicNotFoundException::new);

        mapper.update(entity, requestDto);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public PageResponseDto<CountryAdminResponseDto> listAdmin(CountryCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var entities = repository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<CountryAdminResponseDto>builder()
            .items(entities.stream().map(mapper::entityToAdminDto).toList())
            .total(entities.getTotalElements())
            .build();
    }

    @Override
    public CountryResponseDto get(int id) {
        var entity = repository.findById(id)
            .orElseThrow(PublicNotFoundException::new);

        return mapper.entityToDto(entity);
    }

    @Override
    public PageResponseDto<CountryResponseDto> list(CountryCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var entities = repository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<CountryResponseDto>builder()
            .items(entities.stream().map(mapper::entityToDto).toList())
            .total(entities.getTotalElements())
            .build();
    }

    @Override
    public List<CountryEntity> mapIdsToCountries(List<Integer> ids) {
        return repository.findAllById(ids);
    }
}
