package com.webflick.utils;

import com.webflick.models.webflick.WebFlickResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DynamicControllerGeneratorTest {

    @Test
    void generateRepository() {

        DynamicControllerGenerator dynamicControllerGenerator = new DynamicControllerGenerator();
        String controller = dynamicControllerGenerator.generateController(WebFlickResource.builder()
                .id("1")
                .name("webflick")
                .description("description")
                .type("controller")
                .entityName("webflick")
                .build());

        assertEquals("""                
            
                package com.webflick.controllers;
                              
                 import com.webflick.models.*;
                 import com.webflick.utils.*;
                 import com.webflick.repositories.*;
                 import lombok.RequiredArgsConstructor;
                 import org.springframework.web.bind.annotation.*;
                              
                 import java.util.List;
                 @RequiredArgsConstructor
                 @CrossOrigin(origins = "*")
                 @RestController
                 @RequestMapping(path="/webflick")
                public class WebflickController {
                private final WebflickRepository repository;
                @GetMapping
                public List<Webflick> getAll() {
                return repository.findAll();
                }
                              
                @PostMapping
                public List<Webflick> add(@RequestBody List<Webflick> resources) {
                repository.saveAll(resources);
                }
                }""", controller);

    }
}