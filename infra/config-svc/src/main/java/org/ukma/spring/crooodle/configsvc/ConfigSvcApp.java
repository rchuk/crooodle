package org.ukma.spring.crooodle.configsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigSvcApp {
	public static void main(String[] args) {
		SpringApplication.run(ConfigSvcApp.class, args);
	}
}
