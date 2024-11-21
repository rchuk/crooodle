package org.ukma.spring.crooodle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-override.properties")
class CrooodleApplicationTests {
    @Test
    void contextLoads() {

    }
}
