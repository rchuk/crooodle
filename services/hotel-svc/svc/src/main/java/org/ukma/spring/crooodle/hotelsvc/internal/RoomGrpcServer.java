package org.ukma.spring.crooodle.hotelsvc.internal;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.domain.Pageable;
import org.ukma.spring.crooodle.hotelsvc.grpc.ListRoom;
import org.ukma.spring.crooodle.hotelsvc.grpc.Room;
import org.ukma.spring.crooodle.hotelsvc.grpc.RoomServiceGrpc;
import org.ukma.spring.crooodle.hotelsvc.grpc.UUID;
import org.ukma.spring.crooodle.hotelsvc.mapper.RoomGrpcMapper;
import org.ukma.spring.crooodle.hotelsvc.service.RoomService;

@RequiredArgsConstructor
@GrpcService
public class RoomGrpcServer extends RoomServiceGrpc.RoomServiceImplBase {
    private final RoomGrpcMapper roomGrpcMapper;
    private final RoomService roomService;

    @Override
    public void readRoom(UUID uuid, StreamObserver<Room> responseObserver) {
        var room = roomService.get(java.util.UUID.fromString(uuid.getId()));
        responseObserver.onNext(roomGrpcMapper.roomDtoToRoom(room));
        responseObserver.onCompleted();
    }

    @Override
    public void listHotelRooms(UUID uuid, StreamObserver<ListRoom> responseObserver) {
        var rooms = roomService.listHotelRooms(java.util.UUID.fromString(uuid.getId()), Pageable.unpaged());
        var roomsTransformed = rooms.stream().map(roomGrpcMapper::roomDtoToRoom).toList();
        responseObserver.onNext(ListRoom.newBuilder().addAllRooms(roomsTransformed).build());
        responseObserver.onCompleted();
    }
}
