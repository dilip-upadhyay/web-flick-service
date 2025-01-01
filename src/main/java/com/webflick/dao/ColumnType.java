package com.webflick.dao;

public enum ColumnType {
    STRING("VARCHAR"),
    INTEGER("INT"),
    BOOLEAN("BOOLEAN"),
    DATE("DATE"),
    TIMESTAMP("TIMESTAMP");

    private final String sqlType;

    ColumnType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlType() {
        return sqlType;
    }

    public String getSqlTypeWithLength(int length) {
        if (this == STRING) {
            return sqlType + "(" + length + ")";
        }
        return sqlType;
    }
}
