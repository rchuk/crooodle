package org.ukma.spring.crooodle.service;

import jakarta.validation.Valid;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;

public interface RoomService {
    int create(int hotelId, RoomCreateRequestDto requestDto);
    RoomAdminResponseDto getAdmin(int hotelId, int roomId);
    void edit(int hotelId, int roomId, RoomEditRequestDto requestDto);
    void delete(int hotelId, int roomId);
    PageResponseDto<RoomAdminResponseDto> listAdmin(int hotelId, RoomCriteriaDto criteriaDto);

    RoomResponseDto get(int hotelId, int roomId);

    PageResponseDto<RoomResponseDto> list(int hotelId, @Valid RoomCriteriaDto criteriaDto);
}
