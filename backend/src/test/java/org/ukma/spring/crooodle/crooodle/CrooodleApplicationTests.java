package org.ukma.spring.crooodle.crooodle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.ukma.spring.crooodle.CrooodleApplication;

@SpringBootTest
class CrooodleApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void modulithStructure() {
        ApplicationModules.of(CrooodleApplication.class).verify();
    }
}
