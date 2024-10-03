package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class WeatherForecastResponseDto {
    double latitude;
    double longitude;
    List<Float> dailyMinTemperature;
    List<Float> dailyMaxTemperature;
}
