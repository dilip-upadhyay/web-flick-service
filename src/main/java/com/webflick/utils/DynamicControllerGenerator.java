package com.webflick.utils;

import com.webflick.models.webflick.WebFlickResource;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class DynamicControllerGenerator {

    public String generateController(WebFlickResource webFlickResource) {
        StringWriter entityWriter = new StringWriter();
        entityWriter.append("""
                                
                package com.webflick.controllers;
                                                                 
                 import com.webflick.models.*;
                 import com.webflick.utils.*;
                 import com.webflick.repositories.*;
                 import lombok.RequiredArgsConstructor;
                 import org.springframework.web.bind.annotation.*;
                 
                 import java.util.List;
                 @RequiredArgsConstructor
                 @CrossOrigin(origins = \"*\")
                 @RestController
                 @RequestMapping(path=""");
        entityWriter.append("\"/")
                .append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getEntityName()).toLowerCase())
        .append("\")");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("public class ").append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getName()))
                .append("Controller {");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("private final ")
                .append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getEntityName()))
                .append("Repository")
                .append(" ").append("repository").append(";");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("@GetMapping");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("public List<")
                .append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getEntityName()))
                .append("> getAll() {");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("return repository.findAll();");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("}");
        entityWriter.append(System.lineSeparator());

        entityWriter.append(System.lineSeparator());
        entityWriter.append("@PostMapping");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("public List<")
                .append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getEntityName()))
                .append("> add(@RequestBody List<")
                .append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getEntityName()))
                .append("> resources) {");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("repository.saveAll(resources);");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("}");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("}");



        return entityWriter.toString();
    }
}