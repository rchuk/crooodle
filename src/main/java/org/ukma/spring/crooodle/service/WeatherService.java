package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.WeatherForecastResponseDto;

public interface WeatherService {
    WeatherForecastResponseDto getWeatherForecast(double latitude, double longitude);
}
