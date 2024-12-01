package com.webflick.utils;

import com.webflick.models.webflick.WebFlickResource;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class DynamicRepositoryGenerator {

    public String generateRepository(WebFlickResource webFlickResource) {
        StringWriter entityWriter = new StringWriter();
        entityWriter.append("""
                
                package com.webflick.repositories;
                
                import org.springframework.data.jpa.repository.JpaRepository;
                import org.springframework.stereotype.Repository;
                """);
        entityWriter.append(System.lineSeparator());
        entityWriter.append("import com.webflick.models.").append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getEntityName())).append(";");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("@Repository");
        entityWriter.append(System.lineSeparator());
        entityWriter.append("public interface ").append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getName())).append("Repository").append("  extends JpaRepository<").append(Utils.firstCharToUpperRemoveSpaces(webFlickResource.getEntityName())).append(", Long> {}");

        return entityWriter.toString();
    }
}