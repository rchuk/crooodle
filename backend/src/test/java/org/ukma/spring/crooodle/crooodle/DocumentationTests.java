package org.ukma.spring.crooodle.crooodle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.ukma.spring.crooodle.CrooodleApplication;

@SpringBootTest
public class DocumentationTests {
    private final ApplicationModules modules = ApplicationModules.of(CrooodleApplication.class);

    @Test
    void writeDocumentation() {
        new Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml();
    }
}
