package org.ukma.spring.crooodle.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.service.HotelService;

@Controller
@RequestMapping("/hotel")
public class HotelsController {

    @Autowired
    private HotelService hotelService;

    // View hotel
    @GetMapping("")
    public String getHotel(@RequestParam("id") long hotelId, Model model) {

        Hotel hotel = hotelService.getHotel(hotelId);
        model.addAttribute("hotel", hotel);

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


}
