package org.ukma.spring.crooodle;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class CrooodleApplication {
	@Autowired
	private ApplicationContext applicationContext;
	public static void main(String[] args) {
		SpringApplication.run(CrooodleApplication.class, args);
	}

}
