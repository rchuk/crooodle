package org.ukma.spring.crooodle.hotelsvc.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.ukma.spring.crooodle.hotelsvc.service.RoomSvc;
import org.ukma.spring.crooodle.svc.proto.*;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class RoomGrpcService extends RoomServiceGrpc.RoomServiceImplBase {

	private final RoomSvc roomSvc;

	@Override
	public void getRoomsByHotel(RoomListByHotelRequest req,
															 StreamObserver<RoomResponse> resObserver){

		UUID hotelId = UUID.fromString(req.getHotelId());

		roomSvc.readAllByHotel(hotelId).stream()
			.map(this::toProto)
			.forEach(resObserver::onNext); // stream each room

		resObserver.onCompleted(); // finish streaming
	}

	private RoomResponse toProto(org.ukma.spring.crooodle.hotelsvc.dto.RoomResponseDto dto) {

		HotelResponse hotelProto = HotelResponse.newBuilder()
			.setId(String.valueOf(dto.type().hotel().id()))
			.setOwnerId(String.valueOf(dto.type().hotel().ownerId()))
			.setOwnerName(dto.type().hotel().ownerName())
			.setName(dto.type().hotel().name())
			.setAddress(dto.type().hotel().address())
			.setRoomCount(dto.type().hotel().roomCount())
			.build();

		RoomTypeResponse roomTypeProto = RoomTypeResponse.newBuilder()
			.setId(String.valueOf(dto.type().id()))
			.setName(dto.type().name())
			.setPrice(dto.type().price())
			.setHotel(hotelProto)
			.build();

		return RoomResponse.newBuilder()
			.setId(dto.id().toString())
			.setName(dto.name())
			.setRoomType(roomTypeProto)
			.build();
	}
}
