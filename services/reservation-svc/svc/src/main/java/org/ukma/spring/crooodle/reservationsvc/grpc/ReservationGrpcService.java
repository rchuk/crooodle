package org.ukma.spring.crooodle.reservationsvc.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import com.google.protobuf.Timestamp;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationResponseDto;
import org.ukma.spring.crooodle.reservationsvc.service.ReservationSvc;
import org.ukma.spring.crooodle.svc.proto.*;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class ReservationGrpcService extends ReservationServiceGrpc.ReservationServiceImplBase {
	private final ReservationSvc reservationSvc;

	@Override
	public void getReservationsByUser(ReservationListByUserRequest request,
																		StreamObserver<ReservationListByUserResponse> responseObserver) {

		UUID userId = UUID.fromString(request.getUserId());

		var reservations = reservationSvc.readAllByUser(userId).stream()
			.map(this::toProto)
			.collect(Collectors.toList());

		ReservationListByUserResponse response = ReservationListByUserResponse.newBuilder()
			.addAllReservations(reservations)
			.build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}


	private ReservationResponse toProto(ReservationResponseDto dto) {
		HotelResponse hotelProto = HotelResponse.newBuilder()
			.setId(String.valueOf(dto.room().type().hotel().id()))
			.setOwnerId(String.valueOf(dto.room().type().hotel().ownerId()))
			.setOwnerName(dto.room().type().hotel().ownerName())
			.setName(dto.room().type().hotel().name())
			.setAddress(dto.room().type().hotel().address())
			.setRoomCount(dto.room().type().hotel().roomCount())
			.build();

		RoomTypeResponse roomTypeProto = RoomTypeResponse.newBuilder()
			.setId(String.valueOf(dto.room().type().id()))
			.setName(dto.room().type().name())
			.setPrice(dto.room().type().price())
			.setHotel(hotelProto)
			.build();

		RoomResponse roomProto = RoomResponse.newBuilder()
			.setId(dto.room().id().toString())
			.setName(dto.room().name())
			.setRoomType(roomTypeProto)
			.build();

		return ReservationResponse.newBuilder()
			.setId(dto.id().toString())
			.setRoom(roomProto)
			.setCheckInDate(toProtoTimestamp(dto.checkInDate()))
			.setCheckOutDate(toProtoTimestamp(dto.checkOutDate()))
			.setPrice(dto.price())
			.setState(org.ukma.spring.crooodle.svc.proto.ReservationState.valueOf(dto.state().name()))
			.build();
	}



	private Timestamp toProtoTimestamp(Date date) {
		long millis = date.getTime();
		long seconds = millis / 1000;
		int nanos = (int) ((millis % 1000) * 1_000_000);
		return Timestamp.newBuilder()
			.setSeconds(seconds)
			.setNanos(nanos)
			.build();
	}
}
