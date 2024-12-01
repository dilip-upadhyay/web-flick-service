package com.webflick.controllers;

import com.webflick.WebFlickApplication;
import com.webflick.models.webflick.WebFlickResource;
import com.webflick.utils.DynamicControllerGenerator;
import com.webflick.utils.DynamicEntityGenerator;
import com.webflick.utils.DynamicRepositoryGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/web-flick-resource")
@RequiredArgsConstructor
public class WebFlickResourceController {

    private final DynamicEntityGenerator dynamicEntityGenerator;
    private final DynamicRepositoryGenerator dynamicRepositoryGenerator;
    private final DynamicControllerGenerator dynamicControllerGenerator;

    @PostMapping
    public List<WebFlickResource> generateResource(@RequestBody List<WebFlickResource> webFlickResource) {

        List<WebFlickResource> entities = webFlickResource.stream().filter(resource -> resource.getType().equals("entity")).collect(Collectors.toList());
        List<WebFlickResource> repositories = webFlickResource.stream().filter(resource -> resource.getType().equals("repository")).collect(Collectors.toList());
        List<WebFlickResource> restResources = webFlickResource.stream().filter(resource -> resource.getType().equals("rest")).collect(Collectors.toList());

        entities.forEach(entity -> {
            System.out.println("Entity generated: " + entity.getName());
            try {
                dynamicEntityGenerator.generateEntity(entity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        repositories.forEach(repository -> {
            System.out.println("Repository generated: " + repository.getName());
            try {
                dynamicRepositoryGenerator.generateRepository(repository);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });



        new Thread(WebFlickApplication::restart).start();

        return webFlickResource;
    }

}
