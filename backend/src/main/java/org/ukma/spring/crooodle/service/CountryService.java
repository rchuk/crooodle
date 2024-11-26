package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.entities.CountryEntity;

import java.util.List;

public interface CountryService {
    int create(CountryCreateRequestDto requestDto);
    CountryAdminResponseDto getAdmin(int id);
    void edit(int id, CountryEditRequestDto requestDto);
    void delete(int id);
    PageResponseDto<CountryAdminResponseDto> listAdmin(CountryCriteriaDto criteriaDto, PaginationDto paginationDto);

    CountryResponseDto get(int id);
    PageResponseDto<CountryResponseDto> list(CountryCriteriaDto criteriaDto, PaginationDto paginationDto);

    List<CountryEntity> mapIdsToCountries(List<Integer> ids);
}
