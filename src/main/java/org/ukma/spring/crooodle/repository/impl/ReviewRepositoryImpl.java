package org.ukma.spring.crooodle.repository.impl;

import org.springframework.stereotype.Repository;
import org.ukma.spring.crooodle.model.Review;
import org.ukma.spring.crooodle.repository.ReviewRepository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    @Override
    public void create(Review review) {

    }

    @Override
    public Review getById(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Review> list(Long userId, Long hotelId) {
        return List.of();
    }
}
