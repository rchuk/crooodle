package org.ukma.spring.crooodle.controller.admin;

import org.ukma.spring.crooodle.dto.CityDTO;
import org.ukma.spring.crooodle.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/cities")
public class CityAdminController {

    private final CityService cityService;

    @Autowired
    public CityAdminController(CityService cityService) {
        this.cityService = cityService;
    }

    // Додавання нового міста (для адміністратора)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO cityDTO) {
        CityDTO createdCity = cityService.createCity(cityDTO);
        return ResponseEntity.ok(createdCity);
    }

    // Оновлення міста (для адміністратора)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable Integer id, @RequestBody CityDTO cityDTO) {
        CityDTO updatedCity = cityService.updateCity(id, cityDTO);
        return updatedCity != null ? ResponseEntity.ok(updatedCity) : ResponseEntity.notFound().build();
    }

    // Видалення міста (для адміністратора)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Integer id) {
        return cityService.deleteCity(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
