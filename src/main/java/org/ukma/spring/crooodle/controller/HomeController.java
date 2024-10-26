package org.ukma.spring.crooodle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.ukma.spring.crooodle.service.HotelService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HotelService hotelService;

    @GetMapping("/")
    public String home(Model model) {
        // Завантажити список готелів
        model.addAttribute("hotels", hotelService.listAllHotels());
        return "index";
    }
}
