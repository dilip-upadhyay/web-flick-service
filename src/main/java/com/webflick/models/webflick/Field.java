package com.webflick.models.webflick;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.FieldTypeCustomizer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Field {
    private String name;
    private FieldType type;
    private String value;

}
