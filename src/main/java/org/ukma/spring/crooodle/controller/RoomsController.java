package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.BookingDto;
import org.ukma.spring.crooodle.dto.LoadRoomResponseDto;
import org.ukma.spring.crooodle.dto.RoomCrudRequestDto;
import org.ukma.spring.crooodle.dto.RoomCrudResponseDto;
import org.ukma.spring.crooodle.model.*;
import org.ukma.spring.crooodle.service.BookingService;
import org.ukma.spring.crooodle.service.RoomService;
import org.ukma.spring.crooodle.service.UserExpService;

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
    public RoomCrudResponseDto updateRoom(@RequestBody @Valid RoomCrudRequestDto requestDto) {
        return roomService.updateRoom(requestDto);
    }

    @DeleteMapping("/delete")
    public RoomCrudResponseDto deleteRoom(@PathVariable("id") long roomId) {
        return roomService.deleteRoom(roomId);
    }


}
