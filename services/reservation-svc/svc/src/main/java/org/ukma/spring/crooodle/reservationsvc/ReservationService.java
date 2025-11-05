package org.ukma.spring.crooodle.reservationsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
    basePackages = {
        "org.ukma.spring.crooodle.usersvc.client",
        "org.ukma.spring.crooodle.hotelsvc.client"
    }
)
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationService {
    public static void main(String[] args) {
        SpringApplication.run(ReservationService.class, args);
    }
}
