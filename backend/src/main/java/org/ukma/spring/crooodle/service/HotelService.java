package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;

public interface HotelService {
    Long create(HotelCreateRequestDto requestDto);

    HotelAdminResponseDto getAdmin(Long id);

    HotelResponseDto get(Long id);

    PageResponseDto<HotelAdminResponseDto> listAdmin(HotelCriteriaDto criteriaDto);

    PageResponseDto<HotelResponseDto> list(HotelCriteriaDto criteriaDto);

    void edit(Long id, HotelCreateRequestDto requestDto);

    void delete(Long id);
}
