package org.ukma.spring.crooodle.hotelsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

@EnableFeignClients(basePackages = {"org.ukma.spring.crooodle.usersvc.client"})
@EnableDiscoveryClient
@SpringBootApplication
@EnableRetry
public class HotelService {
    public static void main(String[] args) {
        SpringApplication.run(HotelService.class, args);
    }
}
