package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.entities.RoomGroupEntity;
import org.ukma.spring.crooodle.mappers.RoomGroupMapper;
import org.ukma.spring.crooodle.repository.RoomGroupRepository;
import org.ukma.spring.crooodle.service.RoomGroupService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomGroupServiceImpl implements RoomGroupService {
    private final RoomGroupRepository roomGroupRepository;
    private final RoomGroupMapper mapper;

    @Override
    public long create(RoomGroupCreateRequestDto requestDto) {
        var entity = mapper.dtoToEntity(requestDto);
        // Оновлюємо рейтинг на основі пов'язаних кімнат
        entity.updateRankFromRooms();
        roomGroupRepository.saveAndFlush(entity);

        return entity.getId();
    }

    @Override
    public RoomGroupAdminResponseDto getAdmin(long id) {
        var entity = roomGroupRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("RoomGroup not found"));

        return mapper.entityToAdminDto(entity);
    }

    @Override
    public void edit(long id, RoomGroupEditRequestDto requestDto) {
        var entity = roomGroupRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("RoomGroup not found"));

        mapper.update(entity, requestDto);
        // Оновлюємо рейтинг на основі пов'язаних кімнат
        entity.updateRankFromRooms();
        roomGroupRepository.save(entity);
    }

    @Override
    public void delete(long id) {
        var entity = roomGroupRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("RoomGroup not found"));

        roomGroupRepository.delete(entity);
    }

    @Override
    public PageResponseDto<RoomGroupAdminResponseDto> listAdmin(RoomGroupCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var entities = roomGroupRepository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<RoomGroupAdminResponseDto>builder()
            .items(entities.stream().map(mapper::entityToAdminDto).collect(Collectors.toList()))
            .total(entities.getTotalElements())
            .build();
    }

    @Override
    public RoomGroupResponseDto get(long id) {
        var entity = roomGroupRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("RoomGroup not found"));

        return mapper.entityToDto(entity);
    }

    @Override
    public PageResponseDto<RoomGroupResponseDto> list(RoomGroupCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var groups = roomGroupRepository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<RoomGroupResponseDto>builder()
            .items(groups.stream().map(mapper::entityToDto).collect(Collectors.toList()))
            .total(groups.getTotalElements())
            .build();
    }
}
