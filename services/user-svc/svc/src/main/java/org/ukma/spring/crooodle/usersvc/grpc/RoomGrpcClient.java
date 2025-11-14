package org.ukma.spring.crooodle.usersvc.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
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
		return Flux.create(sink -> {

			StreamObserver<RoomResponse> grpcObserver = new StreamObserver<>() {
				@Override
				public void onNext(RoomResponse value) {
					sink.next(value);
				}

				@Override
				public void onError(Throwable t) {
					sink.error(t);
				}

				@Override
				public void onCompleted() {
					sink.complete();
				}
			};

			getRoomStream(hotelId, grpcObserver);
			sink.onCancel(sink::complete);
		});
	}

	public void getRoomStream(UUID hotelId, io.grpc.stub.StreamObserver<RoomResponse> responseObserver) {
		RoomListByHotelRequest request = RoomListByHotelRequest.newBuilder()
			.setHotelId(hotelId.toString())
			.build();

		roomServiceStub.getRoomsByHotel(request, responseObserver);
	}


}
