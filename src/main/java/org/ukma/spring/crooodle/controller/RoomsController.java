package org.ukma.spring.crooodle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.ukma.spring.crooodle.model.Review;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.model.Booking;
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

    @GetMapping("")
    public String getRoom(@RequestParam("id") long roomId, Model model) {

        // Load room details
        Room room = roomService.getRoom(roomId);
        model.addAttribute("room", room);

        // Load the first 10 reviews for this room
        List<Review> reviews = reviewService.getReviews(roomId);
        if (reviews.size() > 10) {
            reviews = reviews.subList(0, 10);  // Limit to the first 10 reviews
        }
        model.addAttribute("reviews", reviews);

        return "room/details";
    }

    @GetMapping("/book")
    public String book(@RequestParam("roomId") long roomId,
                       @RequestParam("startDate") String startDate,
                       @RequestParam("endDate") String endDate,
                       Model model) {

        // Mocking user (later to load from session token or authentication system)
        User user = new User();

        try {
            Booking booking = bookingService.bookRoom(user, roomId, LocalDate.parse(startDate), LocalDate.parse(endDate));

            model.addAttribute("booking", booking);
            model.addAttribute("message", "Room booked successfully!");

            return "rooms/confirmation"; // confirmation page
        } catch (RuntimeException e) {
            // Handle booking errors
            model.addAttribute("error", "Failed to book room");
            return "rooms/error"; // error page
        }
    }
}
