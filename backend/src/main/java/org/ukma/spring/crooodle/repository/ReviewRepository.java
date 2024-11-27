package org.ukma.spring.crooodle.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.entities.ReviewEntity;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findAllByHotelId(long hotelId);

    List<ReviewEntity> findAllByUserId(long userId);

    List<ReviewEntity> findAllByUserId(long userId, Sort sort);


}
