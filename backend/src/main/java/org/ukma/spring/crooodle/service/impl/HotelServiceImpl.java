package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.entities.HotelEntity;
import org.ukma.spring.crooodle.repository.CountryRepository;
import org.ukma.spring.crooodle.repository.WorldRegionRepository;
import org.ukma.spring.crooodle.repository.HotelRepository;
import org.ukma.spring.crooodle.service.HotelService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final CountryRepository countryRepository;
    private final WorldRegionRepository regionRepository;

    @Override
    public Long create(HotelCreateRequestDto requestDto) {
        var country = countryRepository.findById(requestDto.getCountryId())
            .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        var region = requestDto.getRegionId() != null
            ? regionRepository.findById(requestDto.getRegionId())
            .orElseThrow(() -> new IllegalArgumentException("Region not found"))
            : null;

        HotelEntity hotel = HotelEntity.builder()
            .name(requestDto.getName())
            .address(requestDto.getAddress())
            .country(country)
            .country_region(region)
            .build();

        hotel = hotelRepository.save(hotel);
        return hotel.getId();
    }

    @Override
    public HotelAdminResponseDto getAdmin(Long id) {
        HotelEntity hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        return mapToAdminDto(hotel);
    }

    @Override
    public HotelResponseDto get(Long id) {
        HotelEntity hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        return mapToDto(hotel);
    }

    @Override
    public PageResponseDto<HotelAdminResponseDto> listAdmin(HotelCriteriaDto criteriaDto) {
        List<HotelEntity> hotels = hotelRepository.findAll();

        List<HotelAdminResponseDto> hotelDtos = hotels.stream()
            .map(this::mapToAdminDto)
            .collect(Collectors.toList());

        return PageResponseDto.<HotelAdminResponseDto>builder()
            .total(hotelDtos.size())
            .items(hotelDtos)
            .build();
    }

    @Override
    public PageResponseDto<HotelResponseDto> list(HotelCriteriaDto criteriaDto) {
        List<HotelEntity> hotels = hotelRepository.findAll();

        List<HotelResponseDto> hotelDtos = hotels.stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());

        return PageResponseDto.<HotelResponseDto>builder()
            .total(hotelDtos.size())
            .items(hotelDtos)
            .build();
    }

    @Override
    public void edit(Long id, HotelCreateRequestDto requestDto) {
        HotelEntity hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        hotel.setName(requestDto.getName());
        hotel.setAddress(requestDto.getAddress());
        hotel.setCountry(countryRepository.findById(requestDto.getCountryId())
            .orElseThrow(() -> new IllegalArgumentException("Country not found")));

        hotel.setCountry_region(requestDto.getRegionId() != null
            ? regionRepository.findById(requestDto.getRegionId())
            .orElseThrow(() -> new IllegalArgumentException("Region not found"))
            : null);

        hotelRepository.save(hotel);
    }

    @Override
    public void delete(Long id) {
        HotelEntity hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        hotelRepository.delete(hotel);
    }

    private HotelResponseDto mapToDto(HotelEntity hotel) {
        return HotelResponseDto.builder()
            .id(hotel.getId())
            .name(hotel.getName())
            .address(hotel.getAddress())
            .countryName(hotel.getCountry().getName())
            .regionName(hotel.getCountry_region() != null ? hotel.getCountry_region().getName() : null)
            .build();
    }

    private HotelAdminResponseDto mapToAdminDto(HotelEntity hotel) {
        return HotelAdminResponseDto.builder()
            .id(hotel.getId())
            .name(hotel.getName())
            .address(hotel.getAddress())
            .rankSum(hotel.getRankSum())
            .rankCount(hotel.getRankCount())
            .countryName(hotel.getCountry().getName())
            .regionName(hotel.getCountry_region() != null ? hotel.getCountry_region().getName() : null)
            .build();
    }
}
