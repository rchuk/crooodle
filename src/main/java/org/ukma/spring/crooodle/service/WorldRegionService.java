package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;

public interface WorldRegionService {
    int create(WorldRegionCreateRequestDto requestDto);
    WorldRegionAdminResponseDto getAdmin(int id);
    void edit(int id, WorldRegionEditRequestDto requestDto);
    void delete(int id);
    PageResponseDto<WorldRegionAdminResponseDto> listAdmin(WorldRegionCriteriaDto criteriaDto);

    WorldRegionResponseDto get(int id);
    PageResponseDto<WorldRegionResponseDto> list(WorldRegionCriteriaDto criteriaDto);
}