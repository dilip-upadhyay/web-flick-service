package com.webflick.controllers;

import com.webflick.WebFlickApplication;
import com.webflick.dao.H2DAO;
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
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/web-flick-resource")
@RequiredArgsConstructor
public class WebFlickResourceController {

    private final DynamicEntityGenerator dynamicEntityGenerator;
    private final DynamicRepositoryGenerator dynamicRepositoryGenerator;
    private final DynamicControllerGenerator dynamicControllerGenerator;
    private final H2DAO h2DAO;

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

        restResources.forEach(restResource -> {
            System.out.println("Rest Resource generated: " + restResource.getName());
            try {
                dynamicControllerGenerator.generateController(restResource);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });



        new Thread(WebFlickApplication::restart).start();

        return webFlickResource;
    }

    // Method that take json and creates table in h2 database. use H2DAO class to create table.
    @PostMapping("/create-table")
    public Object createTable(@RequestBody String table) {
        return h2DAO.createTable(table);
    }

    // "/web-flick-resource/create-data-source" create a method that takes json body and creates datasource in h2 database. json will contain datasource name, username, password, hostname and port. also create jdbc template bean for the datasource. Datasource class should be created in configurations package.
    @PostMapping("/create-data-source")
    public Object createDataSource(@RequestBody Map<String, String> dataSource) {
        String dataSource1 = h2DAO.createDataSource(dataSource);
        new Thread(WebFlickApplication::restart).start();
        return dataSource1;
    }

    @PostMapping("/fetch-data-table")
    public Object fetchDataTable(@RequestBody Map<String, String> dataTable) {

        String datasourceName = dataTable.get("datasourceName");
        String tableName = dataTable.get("tableName");
        String sql = "SELECT * FROM " + tableName;
        List<?> data = h2DAO.getAll(sql,  datasourceName);
        new Thread(WebFlickApplication::restart).start();
        return data;
    }


}
