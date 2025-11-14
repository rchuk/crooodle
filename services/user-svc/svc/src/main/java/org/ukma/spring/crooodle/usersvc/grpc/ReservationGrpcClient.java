package org.ukma.spring.crooodle.usersvc.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.ukma.spring.crooodle.svc.proto.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationGrpcClient {

	@GrpcClient("reservationService")
	private ReservationServiceGrpc.ReservationServiceBlockingStub stub;

	public List<ReservationResponse> getReservationsByUser(UUID userId) {
		try {
			ReservationListByUserRequest req =
				ReservationListByUserRequest.newBuilder()
					.setUserId(userId.toString())
					.build();

			return stub.getReservationsByUser(req).getReservationsList();

		} catch (StatusRuntimeException e) {
			Status.Code code = e.getStatus().getCode();

			switch (code) {
				case INVALID_ARGUMENT:
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid userId");

				case NOT_FOUND:
					throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"User is not found");

				case CANCELLED:
					throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT,
						"Request was cancelled");

				case DEADLINE_EXCEEDED:
					throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT,
						"gRPC deadline exceeded");

				case UNIMPLEMENTED:
					throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,
						"Method is not implemented on server");

				case UNAVAILABLE:
					throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
						"Reservation service is unavailable at the moment");

				case INTERNAL:
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Server threw an internal exception");

				default:
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Unknown gRPC error: " + code);
			}
		}
	}
}
