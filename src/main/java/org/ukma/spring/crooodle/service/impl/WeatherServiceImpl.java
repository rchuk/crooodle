package org.ukma.spring.crooodle.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.ukma.spring.crooodle.dto.WeatherForecastResponseDto;
import org.ukma.spring.crooodle.exception.PublicServerException;
import org.ukma.spring.crooodle.service.WeatherService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {
    private static final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);
    @Value("${app.weather.api.url}")
    private String apiUrl;

    private final RestClient restClient;

    @Cacheable(cacheNames = "weather")
    @Override
    public WeatherForecastResponseDto getWeatherForecast(double latitude, double longitude) {
        var url = UriComponentsBuilder
            .fromUriString(apiUrl + "/v1/forecast")
            .queryParam("latitude", "{lat}")
            .queryParam("longitude", "{long}")
            .queryParam("daily", "temperature_2m_min,temperature_2m_max")
            .encode()
            .buildAndExpand(Map.of("lat", latitude, "long", longitude))
            .toUri();
        var rawResponseDto = restClient
            .get()
            .uri(url)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (t1, t2) -> { throw new PublicServerException("Couldn't get weather forecast"); })
            .body(WeatherForecastRawResponseDto.class);
        if (rawResponseDto == null)
            throw new PublicServerException("Couldn't get weather forecast");

        return buildWeatherForecastResponseDto(rawResponseDto);
    }

    public WeatherForecastResponseDto buildWeatherForecastResponseDto(WeatherForecastRawResponseDto rawResponseDto) {
        return WeatherForecastResponseDto.builder()
            .latitude(rawResponseDto.getLatitude())
            .longitude(rawResponseDto.getLongitude())
            .dailyMinTemperature(rawResponseDto.getDaily().getDailyMinTemperature())
            .dailyMaxTemperature(rawResponseDto.getDaily().getDailyMaxTemperature())
            .build();
    }

    @lombok.Value
    public static class WeatherForecastRawResponseDto {
        double latitude;
        double longitude;
        WeatherForecastRawDailyResponseDto daily;
    }

    @lombok.Value
    public static class WeatherForecastRawDailyResponseDto {
        @JsonProperty("temperature_2m_min")
        List<Float> dailyMinTemperature;
        @JsonProperty("temperature_2m_max")
        List<Float> dailyMaxTemperature;
    }
}
