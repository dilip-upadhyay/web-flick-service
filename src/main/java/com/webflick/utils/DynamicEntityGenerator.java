package com.webflick.utils;

import com.webflick.models.webflick.WebFlickResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Slf4j
@Component
public class DynamicEntityGenerator {

    public String generateEntity(WebFlickResource webFlickResource) throws Exception {
        StringWriter entityWriter = new StringWriter();
        entityWriter.append("""
                package com.webflick.models;
                                
                import jakarta.persistence.*;
                import lombok.AllArgsConstructor;
                import lombok.Data;
                import lombok.NoArgsConstructor;
                """);
        entityWriter.append(System.lineSeparator());
        entityWriter.append("""
                
                @Data
                @AllArgsConstructor
                @NoArgsConstructor
                @Entity
                """);
        entityWriter.append(System.lineSeparator());
        entityWriter.append("@Table(name = ").append("\"").append(webFlickResource.getName().toLowerCase()).append("\"").append(")");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("public class ").append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getName())).append(" implements java.io.Serializable{");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("""
                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                Integer id;
                """);

        webFlickResource.getFields().forEach(field -> {


            entityWriter.append("@Column ").append("private ").append(field.getType().getName()).append(" ").append(field.getName()).append(";");
            entityWriter.append(System.lineSeparator());
    });
        entityWriter.append(System.lineSeparator());
        entityWriter.append("}");
        log.info("Entity generated: " + entityWriter.toString());
        JavaClassGenerator.generateClass(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getName()), entityWriter.toString());
        return entityWriter.toString();
    }
}
