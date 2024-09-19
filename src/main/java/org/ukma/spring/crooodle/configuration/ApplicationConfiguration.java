package org.ukma.spring.crooodle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ukma.spring.crooodle.service.impl.RoomServiceImpl;
import org.ukma.spring.crooodle.service.RoomService;


@Configuration
public class ApplicationConfiguration {
    @Bean
    public RoomService getRoomService() {
        return new RoomServiceImpl();
    }
}
