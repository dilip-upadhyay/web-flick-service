package com.webflick.models.webflick;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebFlickResource {


    private String id;
    private String name;
    private String template;
    private String description;
    private String type;
    private Set<Field> fields;
    private String entityName;


}
