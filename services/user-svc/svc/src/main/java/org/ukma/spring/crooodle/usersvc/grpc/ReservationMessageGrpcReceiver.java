//package org.ukma.spring.crooodle.usersvc.grpc;
//
//import io.grpc.stub.StreamObserver;
//import lombok.extern.slf4j.Slf4j;
//import net.devh.boot.grpc.server.service.GrpcService;
//import org.ukma.spring.crooodle.svc.proto.*;
//import org.ukma.spring.crooodle.svc.proto.ReservationResponse;
//import org.ukma.spring.crooodle.svc.proto.ReservationServiceGrpc;
//
//@Slf4j
//@GrpcService
//public class ReservationMessageGrpcReceiver extends ReservationServiceGrpc.ReservationServiceImplBase {
//
//	@Override
//	public void sendReservationToUser(ReservationResponse request,
//																		StreamObserver<ReservationAck> responseObserver) {
//		log.info("Received reservation for user: {}", request.getUser().getName());
//		log.info("Reservation state: {}", request.getState());
//		log.info("Room: {}", request.getRoom().getName());
//
//		// business logic, e.g. notify user or save data
//
//		ReservationAck ack = ReservationAck.newBuilder()
//			.setMessage("Reservation received successfully by user-svc.")
//			.setSuccess(true)
//			.build();
//
//		responseObserver.onNext(ack);
//		responseObserver.onCompleted();
//	}
//}
