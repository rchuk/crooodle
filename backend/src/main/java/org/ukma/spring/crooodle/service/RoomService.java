package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;

public interface RoomService {
    int create(Long hotelId, RoomCreateRequestDto requestDto);

    RoomAdminResponseDto getAdmin(Long hotelId, Long roomId);

    void edit(Long hotelId, Long roomId, RoomEditRequestDto requestDto);

    void delete(Long hotelId, Long roomId);

    PageResponseDto<RoomAdminResponseDto> listAdmin(Long hotelId, RoomCriteriaDto criteriaDto);

    RoomResponseDto get(Long hotelId, Long roomId);

    PageResponseDto<RoomResponseDto> list(Long hotelId, RoomCriteriaDto criteriaDto);
}
