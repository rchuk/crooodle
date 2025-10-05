package org.ukma.spring.crooodle.usersvc.controller;

import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.client.HotelClient;

@RestController
@RequestMapping("/api/hotel")
public class HotelProxyController {

	private final HotelClient hotel;

	public HotelProxyController(HotelClient hotel) {
		this.hotel = hotel;
	}

	@GetMapping("/rooms/{id}/availability")
	public HotelClient.AvailabilityDto availability(@PathVariable long id) {
		return hotel.getAvailabilityInternal(id);
	}
}
