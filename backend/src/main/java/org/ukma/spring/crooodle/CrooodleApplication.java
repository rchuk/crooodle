package org.ukma.spring.crooodle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;
import org.springframework.scheduling.annotation.EnableAsync;

@Modulithic(sharedModules = {"utils"})
@EnableAsync
@SpringBootApplication
public class CrooodleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrooodleApplication.class, args);
    }

}
