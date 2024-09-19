package org.ukma.spring.crooodle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ukma.spring.crooodle.service.BookingService;
import org.ukma.spring.crooodle.service.HotelService;
import org.ukma.spring.crooodle.service.UserExpService;
import org.ukma.spring.crooodle.service.impl.BookingServiceImpl;
import org.ukma.spring.crooodle.service.impl.HotelServiceImpl;
import org.ukma.spring.crooodle.service.impl.UserExpServiceImpl;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public BookingService getBookingService() {
        return new BookingServiceImpl();
    }

    @Bean
    public HotelService getHotelService() {
        return new HotelServiceImpl();
    }

    @Bean
    public UserExpService getUserExpService() {
        return new UserExpServiceImpl();
    }
}
