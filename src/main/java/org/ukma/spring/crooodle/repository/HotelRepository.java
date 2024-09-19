package org.ukma.spring.crooodle.repository;

import org.ukma.spring.crooodle.model.Hotel;

import java.util.List;
import java.util.Map;

public interface HotelRepository {
    Hotel getById(long id);
    List<Hotel> list(Map<String, Object> filters);
}
