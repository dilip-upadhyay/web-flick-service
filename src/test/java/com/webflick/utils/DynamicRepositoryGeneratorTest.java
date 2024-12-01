package com.webflick.utils;

import com.webflick.models.webflick.WebFlickResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicRepositoryGeneratorTest {

    @Test
    void generateRepository() {
        DynamicRepositoryGenerator dynamicRepositoryGenerator = new DynamicRepositoryGenerator();
        String repository = dynamicRepositoryGenerator.generateRepository(WebFlickResource.builder()
                .id("1")
                .name("webflick")
                .template("template")
                .description("description")
                .type("type")
                .entityName("webflick")
                .build());


        assertEquals("""
                
                package com.webflick.repositories;
                                                                                               
                import org.springframework.data.jpa.repository.JpaRepository;
                import org.springframework.stereotype.Repository;
                                                                                               
                import com.webflick.models.Webflick;
                @Repository
                public interface WebflickRepository  extends JpaRepository<Webflick, Long> {}""", repository);
    }
}