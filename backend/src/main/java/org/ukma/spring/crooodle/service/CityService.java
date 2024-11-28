package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.CityDTO;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<CityDTO> getAllCities();
    Optional<CityDTO> getCityById(Integer id);
    CityDTO createCity(CityDTO cityDTO);
    CityDTO updateCity(Integer id, CityDTO cityDTO);
    boolean deleteCity(Integer id);
}
