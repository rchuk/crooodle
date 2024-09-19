package org.ukma.spring.crooodle.repository.impl;

import org.springframework.stereotype.Repository;
import org.ukma.spring.crooodle.model.Hotel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class HotelRepositoryImpl {
    private final List<Hotel> testHotels = List.of(
            Hotel.builder()
                    .id(1L)
                    .name("Hotel one")
                    .address("Somewhere")
                    .ranking(5.0)
                    .build(),
            Hotel.builder()
                    .id(2L)
                    .name("Hotel two")
                    .address("Somewhere Else")
                    .ranking(2.0)
                    .build()
    );

    public List<Hotel> filterHotels(Map<String, Object> filters) {
        return testHotels.stream()
                .filter(hotel -> applyFilters(hotel, filters))
                .collect(Collectors.toList());
    }

    private boolean applyFilters(Hotel hotel, Map<String, Object> filters) {
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String fieldName = filter.getKey();
            Object expectedValue = filter.getValue();

            try {
                if(fieldName.contains("Min")){
                    fieldName = fieldName.replace("Min", "");
                    Field field = hotel.getClass().getDeclaredField(fieldName);

                    field.setAccessible(true);
                    Object actualValue = field.get(hotel);

                    if (expectedValue instanceof Number && actualValue instanceof Number) {
                        double minValue = ((Number) expectedValue).doubleValue();
                        double actual = ((Number) actualValue).doubleValue();

                        if (actual < minValue) {
                            return false;
                        }
                    }
                    else
                        return false;

                } else  if (fieldName.contains("Max")) {
                    fieldName = fieldName.replace("Max", "");
                    Field field = hotel.getClass().getDeclaredField(fieldName);

                    field.setAccessible(true);
                    Object actualValue = field.get(hotel);

                    if (expectedValue instanceof Number && actualValue instanceof Number) {
                        double maxValue = ((Number) expectedValue).doubleValue();
                        double actual = ((Number) actualValue).doubleValue();

                        if (actual > maxValue) {
                            return false;
                        }
                    } else
                        return false;
                }
                else {
                    Field field = hotel.getClass().getDeclaredField(fieldName);

                    field.setAccessible(true);
                    Object actualValue = field.get(hotel);

                    if (!expectedValue.equals(actualValue)) {
                        return false;
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true; // Otherwise everything is good
    }
}
