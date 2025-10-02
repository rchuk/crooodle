package org.ukma.spring.crooodle.hotelsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.hotelsvc.entity.HotelEntity;

import java.util.UUID;

public interface HotelRepo extends JpaRepository<HotelEntity, UUID> {

}
