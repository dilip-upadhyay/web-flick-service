package com.webflick.controllers;

import com.webflick.WebFlickApplication;
import com.webflick.models.webflick.WebFlickResource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping (path = "/web-flick-resource")
@RequiredArgsConstructor
public class WebFlickResourceController {

    private final ConfigurableApplicationContext configurableApplicationContext;

    @PostMapping
    public List<WebFlickResource> generateResource(@RequestBody List<WebFlickResource> webFlickResource) {

        List<WebFlickResource> entities = webFlickResource.stream().filter(resource -> resource.getType().equals("entity")).collect(Collectors.toList());
        List<WebFlickResource> repository = webFlickResource.stream().filter(resource -> resource.getType().equals("repository")).collect(Collectors.toList());
        List<WebFlickResource> restResources = webFlickResource.stream().filter(resource -> resource.getType().equals("rest")).collect(Collectors.toList());

        entities.forEach(entity -> {
            System.out.println("Entity generated: " + entity.getName());
            entity.getName();
        });

        WebFlickApplication.restart();

        return webFlickResource;
    }

}
