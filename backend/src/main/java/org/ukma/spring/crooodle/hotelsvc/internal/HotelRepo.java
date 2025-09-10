package org.ukma.spring.crooodle.hotelsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelRepo extends JpaRepository<HotelEntity, UUID> {

}