package org.ukma.spring.crooodle.reservationsvc.service;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.grpc.RoomServiceGrpc;
import org.ukma.spring.crooodle.reservationsvc.repository.ReservationRepository;
import org.ukma.spring.crooodle.usersvc.grpc.ProfileServiceGrpc;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @GrpcClient("profileService")
    private ProfileServiceGrpc.ProfileServiceBlockingStub profileService;
    @GrpcClient("roomService")
    private RoomServiceGrpc.RoomServiceBlockingStub roomService;
}
