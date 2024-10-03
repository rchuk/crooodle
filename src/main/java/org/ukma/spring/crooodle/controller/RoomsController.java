package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.BookingDto;
import org.ukma.spring.crooodle.dto.LoadRoomResponseDto;
import org.ukma.spring.crooodle.dto.common.PublicErrorDto;
import org.ukma.spring.crooodle.model.*;
import org.ukma.spring.crooodle.service.BookingService;
import org.ukma.spring.crooodle.service.RoomService;
import org.ukma.spring.crooodle.service.UserExpService;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomsController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserExpService reviewService;

    // Load room
    @GetMapping("")
    public LoadRoomResponseDto getRoom(@PathVariable("id") long roomId) {
        return roomService.loadRoom(roomId);
    }

    // Book Room
    @PostMapping("/book")
    public BookingDto book(@RequestBody @Valid BookingDto bookingDto) {
        return bookingService.bookRoom(bookingDto);
    }

    @PutMapping("/update")
    public String updateRoom(@RequestParam("roomId") long roomId,
                             @RequestParam("hotelId") long hotelId,
                             @RequestParam("description") String description,
                             @RequestParam("price") int price,
                             Model model) {
        try {
            Room room = roomService.getRoom(roomId);
            room.setPricePerNight(price);

            roomService.updateRoom(room);
            model.addAttribute("message", "Room updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update room");
        }

        return "redirect:/rooms?id=" + roomId;  // updated room details page
    }

    @DeleteMapping("/delete")
    public String deleteRoom(@RequestParam("roomId") long roomId, Model model) {
        try {
            roomService.deleteRoom(roomId);
            model.addAttribute("message", "Room deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete room");
        }

        return "redirect:/rooms";  // rooms listing page
    }


}
