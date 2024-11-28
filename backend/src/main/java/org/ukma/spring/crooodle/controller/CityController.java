package org.ukma.spring.crooodle.controller;

import org.ukma.spring.crooodle.dto.CityDTO;
import org.ukma.spring.crooodle.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // Отримання всіх міст (для звичайних користувачів)
    @GetMapping
    public List<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    // Отримання міста за ID (для звичайних користувачів)
    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Integer id) {
        return cityService.getCityById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
