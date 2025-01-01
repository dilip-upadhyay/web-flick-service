package com.webflick.dao;

import lombok.Getter;

@Getter
public enum ColumnType {
    STRING("VARCHAR"),
    INTEGER("INT"),
    BOOLEAN("BOOLEAN"),
    DATE("DATE"),
    TIMESTAMP("TIMESTAMP"),
    FLOAT("FLOAT"),
    DOUBLE("DOUBLE"),
    LONG("BIGINT"),
    SHORT("SMALLINT"),
    BYTE("TINYINT"),
    BINARY("BINARY"),
    VARBINARY("VARBINARY"),
    DECIMAL("DECIMAL"),
    NUMERIC("NUMERIC"),
    CHAR("CHAR"),
    TEXT("TEXT"),
    CLOB("CLOB"),
    BLOB("BLOB"),
    UUID("UUID");

    private final String sqlType;

    ColumnType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlTypeWithLength(int length) {
        if (this == STRING || this == CHAR || this == VARBINARY) {
            return sqlType + "(" + length + ")";
        }
        return sqlType;
    }
}
