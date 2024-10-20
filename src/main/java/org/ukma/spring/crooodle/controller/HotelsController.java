package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.service.HotelService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hotel")
@Slf4j
public class HotelsController {
    private final HotelService hotelService;

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/weather")
    public WeatherForecastResponseDto getHotelWeatherForecast(@PathVariable long id) {
        log.info("Getting weather forecast for hotel with id {}", id);
        return hotelService.getHotelWeatherForecast(id);
    }

    // View hotel
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public HotelResponseDto getHotel(@PathVariable long id) {
        log.info("Getting hotel with id {}", id);
        return hotelService.getHotel(id);
    }

    // Create hotel
    @PreAuthorize("hasRole('ROLE_HOTEL_OWNER')")
    @PostMapping()
    public long createHotel(@RequestBody @Valid HotelCreateRequestDto requestDto) {
        log.info("Creating hotel with name {}", requestDto.getName());
        return hotelService.createHotel(requestDto);
    }

    // Update hotel
    @PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
    @PutMapping("/{id}")
    public void updateHotel(@PathVariable long id, @RequestBody @Valid HotelUpdateRequestDto requestDto) {
        log.info("Updating hotel with id {}", id);
        hotelService.updateHotel(id, requestDto);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/rooms")
    public List<RoomTypeWithCountResponseDto> getHotelRoomsByType(@PathVariable long id) {
        log.info("Getting room types for hotel with id {}", id);
        return hotelService.getAvailableRoomTypes(id);
    }

    // Delete hotel
    @PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable long id) {
        log.info("Deleting hotel with id {}", id);
        hotelService.deleteHotel(id);
    }
}
