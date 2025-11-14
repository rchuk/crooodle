package org.ukma.spring.crooodle.usersvc.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.usersvc.exception.EntityNotFoundException;
import org.ukma.spring.crooodle.svc.proto.RoomListByHotelRequest;
import org.ukma.spring.crooodle.svc.proto.RoomResponse;
import org.ukma.spring.crooodle.svc.proto.RoomServiceGrpc;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomGrpcClient {
	@GrpcClient("roomService")
	private RoomServiceGrpc.RoomServiceStub roomServiceStub;

	public Flux<RoomResponse> runRoomStream(UUID hotelId) {
		if (hotelId == null) {
			return Flux.error(new IllegalArgumentException("hotelId cannot be null"));
		}

		return Flux.<RoomResponse>create(sink -> {

			StreamObserver<RoomResponse> grpcObserver = new StreamObserver<>() {
				@Override
				public void onNext(RoomResponse value) {
					sink.next(value);
				}

				@Override
				public void onError(Throwable t) {
					sink.error(mapGrpcError(t, hotelId));
				}

				@Override
				public void onCompleted() {
					sink.complete();
				}
			};

			getRoomStream(hotelId, grpcObserver);
			sink.onCancel(sink::complete);

		}).onErrorMap(t -> mapGrpcError(t, hotelId));
	}

	public void getRoomStream(UUID hotelId, StreamObserver<RoomResponse> responseObserver) {
		RoomListByHotelRequest request = RoomListByHotelRequest.newBuilder()
			.setHotelId(hotelId.toString())
			.build();

		roomServiceStub.getRoomsByHotel(request, responseObserver);
	}

	private RuntimeException mapGrpcError(Throwable t, UUID hotelId) {
		if (t instanceof io.grpc.StatusRuntimeException sre) {
            return switch (sre.getStatus().getCode()) {
                case NOT_FOUND -> new EntityNotFoundException(hotelId, "Hotel");
                case INVALID_ARGUMENT -> new IllegalArgumentException("Invalid hotelId");
                case UNAVAILABLE, DEADLINE_EXCEEDED, INTERNAL ->
                        new IllegalStateException("gRPC internal error on streaming rooms", t);
                default -> new IllegalStateException("Unknown gRPC error: " + sre.getStatus(), t);
            };
		}
		return new IllegalStateException("Unexpected error while calling gRPC", t);
	}
}
