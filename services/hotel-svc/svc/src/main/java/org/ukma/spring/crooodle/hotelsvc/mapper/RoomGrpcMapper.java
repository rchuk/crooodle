package org.ukma.spring.crooodle.hotelsvc.mapper;

import org.mapstruct.Mapper;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomDto;
import org.ukma.spring.crooodle.hotelsvc.grpc.Room;

@Mapper(componentModel = "spring")
public interface RoomGrpcMapper {
    Room roomDtoToRoom(RoomDto room);
}
