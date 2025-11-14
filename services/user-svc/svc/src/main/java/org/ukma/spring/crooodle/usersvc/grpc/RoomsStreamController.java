package org.ukma.spring.crooodle.usersvc.grpc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.svc.proto.RoomResponse;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/user-stream")
@RequiredArgsConstructor
public class RoomsStreamController {
	private final RoomGrpcClient roomGrpcClient;

	@GetMapping(value = "/hotel/{hotelId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<RoomResponse> streamRoomsByHotel(@PathVariable UUID hotelId) {
		return roomGrpcClient.runRoomStream(hotelId);
	}
}
