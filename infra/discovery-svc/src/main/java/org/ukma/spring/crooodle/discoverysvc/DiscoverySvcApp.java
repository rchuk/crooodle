package org.ukma.spring.crooodle.discoverysvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoverySvcApp {
	public static void main(String[] args) {
		SpringApplication.run(DiscoverySvcApp.class, args);
	}
}
