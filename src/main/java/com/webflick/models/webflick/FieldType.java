package com.webflick.models.webflick;

import lombok.Getter;

@Getter
public enum FieldType {
   
    TEXT("String"),
    NUMBER("int"),
    DATE("java.sql.Timestamp"),
    BOOLEAN("boolean"),
    FILE("byte[]");

    private final String name;

    FieldType(String string) {
        this.name = string;
    }

}
