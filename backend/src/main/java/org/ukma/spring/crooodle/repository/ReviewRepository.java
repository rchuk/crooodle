package org.ukma.spring.crooodle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.entities.ReviewEntity;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Page<ReviewEntity> findAllByHotelId(long hotelId, Pageable pageable);

    Page<ReviewEntity> findAllByUserId(long userId, Pageable pageable);

    List<ReviewEntity> findAllByUserId(long userId, Sort sort);


}
