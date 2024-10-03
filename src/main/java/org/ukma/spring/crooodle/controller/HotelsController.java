package org.ukma.spring.crooodle.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.WeatherForecastResponseDto;
import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.service.HotelService;

@RestController
@RequestMapping("/hotel")
public class HotelsController {

    @Autowired
    private HotelService hotelService;

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/weather")
    public WeatherForecastResponseDto getHotelWeatherForecast(@PathVariable("id") long hotelId) {
        return hotelService.getHotelWeatherForecast(hotelId);
    }

    // View hotel
    @GetMapping("")
    public String getHotel(@RequestParam("id") long hotelId, Model model) {

        Hotel hotel = hotelService.getHotel(hotelId);
        model.addAttribute("hotel", hotel);

        return "hotel/details";
    }

    // Create hotel
    @PostMapping("/create")
    public String createHotel(@RequestParam("name") String name,
                         @RequestParam("address") String address,
                         @RequestParam("ranking") double ranking,
                         @RequestParam("totalRanks") int totalRanks,
                         Model model) {
        try {
            Hotel hotel = new Hotel();
            hotel.setName(name);
            hotel.setAddress(address);
            hotel.setRanking(ranking);
            hotel.setTotalRanks(totalRanks);

            hotelService.createHotel(hotel);
            model.addAttribute("message", "Hotel created successfully!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "hotel/details";
    }

    // Update hotel
    @PutMapping("/update")
    public String updateHotel(@RequestParam("hotelId") long hotelId,
                         @RequestParam("name") String name,
                         @RequestParam("address") String address,
                         @RequestParam("ranking") double ranking,
                         @RequestParam("totalRanks") int totalRanks,
                         Model model) {
        try {
            Hotel hotel = hotelService.getHotel(hotelId);
            hotel.setName(name);
            hotel.setAddress(address);
            hotel.setRanking(ranking);
            hotel.setTotalRanks(totalRanks);

            hotelService.updateHotel(hotel);
            model.addAttribute("message", "Hotel updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "hotel/details";
    }

    // Delete hotel
    @DeleteMapping("/delete")
    public String deleteHotel(@RequestParam("hotelId") long hotelId, Model model) {
        try {
            hotelService.deleteHotel(hotelId);
            model.addAttribute("message", "Hotel deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "hotels";
    }


}
