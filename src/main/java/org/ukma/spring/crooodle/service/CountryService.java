package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;

public interface CountryService {
    int create(CountryCreateRequestDto requestDto);
    CountryAdminResponseDto getAdmin(int id);
    void edit(int id, CountryEditRequestDto requestDto);
    void delete(int id);
    PageResponseDto<CountryAdminResponseDto> listAdmin(CountryCriteriaDto criteriaDto);

    CountryResponseDto get(int id);
    PageResponseDto<CountryResponseDto> list(CountryCriteriaDto criteriaDto);
}
