package org.ukma.spring.crooodle.roomsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepo extends JpaRepository<RoomEntity, UUID> {
}
