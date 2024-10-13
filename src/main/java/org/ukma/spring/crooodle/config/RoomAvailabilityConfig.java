package org.ukma.spring.crooodle.config;

import org.external.RoomAvailabilityChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomAvailabilityConfig {

    @Bean
    public RoomAvailabilityChecker roomAvailabilityChecker() {
        return new RoomAvailabilityChecker();
    }
}
