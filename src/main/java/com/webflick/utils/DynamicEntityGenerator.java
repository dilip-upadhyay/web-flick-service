package com.webflick.utils;

import com.webflick.models.webflick.WebFlickResource;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class DynamicEntityGenerator {

    public String generateRepository(WebFlickResource webFlickResource) {
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
        entityWriter.append("public class ").append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getName())).append(" implements java.io.Serializable{");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("""
                @Column
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                Integer id;
                """);

        webFlickResource.getFields().forEach(field -> {


            entityWriter.append("@Column ").append("private ").append(field.getType().getName()).append(" ").append(field.getName()).append(";");
            entityWriter.append(System.lineSeparator());
    });
        entityWriter.append(System.lineSeparator());
        entityWriter.append("}");
        return entityWriter.toString();
    }
}
