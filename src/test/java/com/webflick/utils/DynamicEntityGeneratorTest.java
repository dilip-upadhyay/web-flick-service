package com.webflick.utils;

import com.webflick.models.webflick.Field;
import com.webflick.models.webflick.FieldType;
import com.webflick.models.webflick.WebFlickResource;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Set;

class DynamicEntityGeneratorTest {


    @Test
    void generateRepository() {

        WebFlickResource webFlickResource = WebFlickResource.builder()
                .name("User")
                .description("User Resource")
                .type("Entity")
                .fields(Set.of(
                        Field.builder().name("name").type(FieldType.TEXT).build(),
                        Field.builder().name("age").type(FieldType.NUMBER).build(),
                        Field.builder().name("address").type(FieldType.TEXT).build(),
                        Field.builder().name("active").type(FieldType.BOOLEAN).build(),
                        Field.builder().name("dob").type(FieldType.DATE).build()
                ))
                .build();

        String generatedResource = new DynamicEntityGenerator().generateRepository(webFlickResource);
        Assert.notNull(generatedResource, "Generated Resource is not null");
    }
}