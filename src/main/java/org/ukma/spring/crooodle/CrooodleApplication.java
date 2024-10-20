package org.ukma.spring.crooodle;

import org.slf4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrooodleApplication {
	static final Logger logger = LoggerFactory.getLogger(CrooodleApplication.class);
	private static final Marker IMPORTANT = MarkerFactory.getMarker("IMPORTANT");

	public static void main(String[] args) {
		// Marker and MDC setup
		MDC.put("name", "test");
		logger.info("Before starting application");
		logger.warn(IMPORTANT, "This is an important message");

		SpringApplication.run(CrooodleApplication.class, args);

		// MDS usage
		logger.info("After starting application");
		logger.warn("Hello, {}!", MDC.get("name"));
		MDC.clear();
	}

}
