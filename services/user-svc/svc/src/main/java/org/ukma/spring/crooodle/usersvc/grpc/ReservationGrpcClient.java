package org.ukma.spring.crooodle.usersvc.grpc;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.svc.proto.ReservationListByUserRequest;
import org.ukma.spring.crooodle.svc.proto.ReservationListByUserResponse;
import org.ukma.spring.crooodle.svc.proto.ReservationResponse;
import org.ukma.spring.crooodle.svc.proto.ReservationServiceGrpc;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationGrpcClient {

	@GrpcClient("reservationService")
	private ReservationServiceGrpc.ReservationServiceBlockingStub reservationServiceBlockingStub;

	public List<ReservationResponse> getReservationsByUser(UUID userId){
		ReservationListByUserRequest req = ReservationListByUserRequest.newBuilder()
			.setUserId(userId.toString())
			.build();

		ReservationListByUserResponse res = reservationServiceBlockingStub.getReservationsByUser(req);
		return res.getReservationsList();
	}

}
