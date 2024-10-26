package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.service.HotelService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/hotels")
@Slf4j
public class HotelsController {

    private final HotelService hotelService;

    // Головна сторінка для перегляду всіх готелів
    @GetMapping
    public String listHotels(Model model) {
        log.info("Loading list of hotels");
        List<HotelResponseDto> hotels = hotelService.listAllHotels();
        model.addAttribute("hotels", hotels);
        return "index"; // Відображення на головній сторінці
    }

    // Перегляд інформації про готель
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public String getHotel(@PathVariable long id, Model model) {
        log.info("Getting hotel with id {}", id);
        HotelResponseDto hotel = hotelService.getHotel(id);
        model.addAttribute("hotel", hotel);
        return "hotel_details"; // Відображення на сторінці детальної інформації про готель
    }

    // Перегляд погоди для готелю
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/weather")
    public String getHotelWeatherForecast(@PathVariable long id, Model model) {
        log.info("Getting weather forecast for hotel with id {}", id);
        WeatherForecastResponseDto weather = hotelService.getHotelWeatherForecast(id);
        model.addAttribute("weather", weather);
        return "hotel_weather"; // Відображення на сторінці погоди готелю
    }

    // Перегляд кімнат готелю
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/rooms")
    public String getHotelRoomsByType(@PathVariable long id, Model model) {
        log.info("Getting room types for hotel with id {}", id);
        List<RoomTypeWithCountResponseDto> rooms = hotelService.getAvailableRoomTypes(id);
        model.addAttribute("rooms", rooms);
        return "hotel_rooms"; // Відображення на сторінці списку кімнат готелю
    }

    // Створення нового готелю (форма)
    @PreAuthorize("hasRole('ROLE_HOTEL_OWNER')")
    @GetMapping("/new")
    public String showCreateHotelForm(Model model) {
        model.addAttribute("hotelForm", new HotelForm()); // Використання нового об'єкта для форми
        return "hotel_create"; // Відображення форми для створення готелю
    }

    // Обробка створення готелю
    @PreAuthorize("hasRole('ROLE_HOTEL_OWNER')")
    @PostMapping
    public String createHotel(@ModelAttribute @Valid HotelForm hotelForm) {
        log.info("Creating hotel with name {}", hotelForm.getName());

        // Перетворення об'єкта форми на DTO
        HotelCreateRequestDto requestDto = new HotelCreateRequestDto(
                hotelForm.getName(),
                hotelForm.getAddress(),
                hotelForm.getLatitude(),
                hotelForm.getLongitude()
        );

        hotelService.createHotel(requestDto);
        return "redirect:/hotels"; // Редирект на список готелів після створення
    }

    // Оновлення готелю (форма)
    @PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
    @GetMapping("/{id}/edit")
    public String showUpdateHotelForm(@PathVariable long id, Model model) {
        log.info("Loading hotel update form for id {}", id);
        HotelResponseDto hotel = hotelService.getHotel(id);
        HotelForm hotelForm = new HotelForm(
                hotel.getName(),
                hotel.getAddress(),
                hotel.getLatitude(),
                hotel.getLongitude()
        );
        model.addAttribute("hotelForm", hotelForm);
        model.addAttribute("hotelId", id);
        return "hotel_edit"; // Відображення форми для оновлення готелю
    }

    // Обробка оновлення готелю
    @PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
    @PostMapping("/{id}")
    public String updateHotel(@PathVariable long id, @ModelAttribute @Valid HotelForm hotelForm) {
        log.info("Updating hotel with id {}", id);

        // Перетворення об'єкта форми на DTO
        HotelUpdateRequestDto requestDto = new HotelUpdateRequestDto(
                hotelForm.getName(),
                hotelForm.getAddress(),
                hotelForm.getLatitude(),
                hotelForm.getLongitude()
        );

        hotelService.updateHotel(id, requestDto);
        return "redirect:/hotels/" + id; // Редирект на сторінку готелю після оновлення
    }

    // Видалення готелю
    @PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteHotel(@PathVariable long id) {
        log.info("Deleting hotel with id {}", id);
        hotelService.deleteHotel(id);
        return "redirect:/hotels"; // Редирект на список готелів після видалення
    }
}
