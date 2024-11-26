package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;

public interface HotelService {
    long create(HotelCreateRequestDto requestDto);

    HotelAdminResponseDto getAdmin(long id);

    HotelResponseDto get(long id);

    PageResponseDto<HotelAdminResponseDto> listAdmin(HotelCriteriaDto criteriaDto, PaginationDto paginationDto);

    PageResponseDto<HotelResponseDto> list(HotelCriteriaDto criteriaDto, PaginationDto paginationDto);

    void edit(long id, HotelEditRequestDto requestDto);

    void delete(long id);
}
