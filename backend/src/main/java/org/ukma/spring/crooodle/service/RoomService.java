package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;

public interface RoomService {
    long create(RoomCreateRequestDto requestDto);

    RoomAdminResponseDto getAdmin(long id);

    void edit(long id, RoomEditRequestDto requestDto);

    void delete(long id);

    PageResponseDto<RoomAdminResponseDto> listAdmin(RoomCriteriaDto criteriaDto, PaginationDto paginationDto);

    RoomResponseDto get(long id);

    PageResponseDto<RoomResponseDto> list(RoomCriteriaDto criteriaDto, PaginationDto paginationDto);
}
