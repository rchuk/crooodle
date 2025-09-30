package org.ukma.spring.crooodle.hotelsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"org.ukma.spring.crooodle.usersvc.client"})
@EnableDiscoveryClient
@SpringBootApplication
public class HotelSvc {
    public static void main(String[] args) {
        SpringApplication.run(HotelSvc.class, args);
    }
}
