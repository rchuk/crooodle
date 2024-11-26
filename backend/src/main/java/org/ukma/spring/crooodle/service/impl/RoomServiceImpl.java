package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.mappers.RoomMapper;
import org.ukma.spring.crooodle.repository.RoomRepository;
import org.ukma.spring.crooodle.service.RoomService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    private final RoomMapper mapper;

    @Override
    public long create(RoomCreateRequestDto requestDto) {
        var entity = mapper.dtoToEntity(requestDto);
        roomRepository.saveAndFlush(entity);

        return entity.getId();
    }

    @Override
    public RoomAdminResponseDto getAdmin(long id) {
        var entity = roomRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        return mapper.entityToAdminDto(entity);
    }

    @Override
    public void edit(long id, RoomEditRequestDto requestDto) {
        var entity = roomRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        mapper.update(entity, requestDto);

        roomRepository.save(entity);
    }

    @Override
    public void delete(long id) {
        var entity = roomRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        roomRepository.delete(entity);
    }

    @Override
    public PageResponseDto<RoomAdminResponseDto> listAdmin(RoomCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var entities = roomRepository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<RoomAdminResponseDto>builder()
            .items(entities.stream().map(mapper::entityToAdminDto).collect(Collectors.toList()))
            .total(entities.getTotalElements())
            .build();
    }

    @Override
    public RoomResponseDto get(long id) {
        var entity = roomRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        return mapper.entityToDto(entity);
    }

    @Override
    public PageResponseDto<RoomResponseDto> list(RoomCriteriaDto criteriaDto, PaginationDto paginationDto) {
        var spec = mapper.criteriaToSpec(criteriaDto);
        var rooms = roomRepository.findAll(spec, paginationDto.toPageable());

        return PageResponseDto.<RoomResponseDto>builder()
            .items(rooms.stream().map(mapper::entityToDto).collect(Collectors.toList()))
            .total(rooms.getTotalElements())
            .build();
    }
}
