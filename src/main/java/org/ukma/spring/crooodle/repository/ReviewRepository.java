package org.ukma.spring.crooodle.repository;

import org.ukma.spring.crooodle.model.Review;

import java.util.List;

public interface ReviewRepository {
    void create(Review review);
    Review getById(long id);
    void delete(long id);
    List<Review> list(Long userId, Long hotelId);
}
