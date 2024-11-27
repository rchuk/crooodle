package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;

public interface RoomGroupService {
    long create(RoomGroupCreateRequestDto requestDto);

    RoomGroupAdminResponseDto getAdmin(long id);

    void edit(long id, RoomGroupEditRequestDto requestDto);

    void delete(long id);

    PageResponseDto<RoomGroupAdminResponseDto> listAdmin(RoomGroupCriteriaDto criteriaDto, PaginationDto paginationDto);

    RoomGroupResponseDto get(long id);

    PageResponseDto<RoomGroupResponseDto> list(RoomGroupCriteriaDto criteriaDto, PaginationDto paginationDto);
}
