package org.ukma.spring.crooodle.service.impl;

import org.ukma.spring.crooodle.dto.CityDTO;
import org.ukma.spring.crooodle.mappers.CityMapper;
import org.ukma.spring.crooodle.entities.CityEntity;
import org.ukma.spring.crooodle.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.service.CityService;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityDTO> getAllCities() {
        List<CityEntity> cities = cityRepository.findAll();
        return cities.stream().map(cityMapper::entityToDto).toList();
    }

    @Override
    public Optional<CityDTO> getCityById(Integer id) {
        Optional<CityEntity> cityEntity = cityRepository.findById(id);
        return cityEntity.map(cityMapper::entityToDto);
    }

    @Override
    public CityDTO createCity(CityDTO cityDTO) {
        CityEntity cityEntity = cityMapper.dtoToEntity(cityDTO); // Використовуємо DTO
        cityEntity = cityRepository.save(cityEntity);
        return cityMapper.entityToDto(cityEntity);
    }

    @Override
    public CityDTO updateCity(Integer id, CityDTO cityDTO) {
        if (!cityRepository.existsById(id)) {
            return null; // або можна викинути виключення
        }
        cityDTO.setId(id); // Встановлюємо id для оновлення
        CityEntity cityEntity = cityMapper.dtoToEntity(cityDTO); // Використовуємо DTO
        cityEntity = cityRepository.save(cityEntity);
        return cityMapper.entityToDto(cityEntity);
    }

    @Override
    public boolean deleteCity(Integer id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
