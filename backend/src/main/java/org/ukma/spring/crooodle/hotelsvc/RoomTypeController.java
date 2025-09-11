package org.ukma.spring.crooodle.hotelsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/roomtype")
@RestController
public class RoomTypeController {

    private final RoomTypeSvc typeSvc;

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PostMapping
    public UUID create(@RequestBody RoomTypeDto typeDto){
        return typeSvc.create(typeDto);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @GetMapping("/{id}")
    public RoomTypeDto read(@PathVariable UUID id){
        return typeSvc.read(id);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @GetMapping("/{hotel_id}")
    public List<RoomTypeDto> readAllByHotel(@PathVariable UUID hotel_id){
        return typeSvc.readAllByHotel(hotel_id);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody RoomTypeDto typeDtoToUpdate){
        typeSvc.update(id, typeDtoToUpdate);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        typeSvc.delete(id);
    }
}
