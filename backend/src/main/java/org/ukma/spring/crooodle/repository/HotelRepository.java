package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ukma.spring.crooodle.entities.HotelEntity;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}
