package com.webflick.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflick.dao.ColumnType;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

public class DTOClassGenerator {

    public static String generateDTOClassFromJson(String jsonString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        String className = rootNode.get("tableName").asText();
        JsonNode columnsNode = rootNode.get("columns");

        StringWriter classWriter = new StringWriter();
        classWriter.append("package com.webflick.dto;").append(System.lineSeparator());
        classWriter.append(System.lineSeparator());
        classWriter.append("import lombok.Data;").append(System.lineSeparator());
        classWriter.append(System.lineSeparator());
        classWriter.append("@Data").append(System.lineSeparator());
        classWriter.append("public class ").append(className).append("DTO {").append(System.lineSeparator());

        Iterator<Map.Entry<String, JsonNode>> fields = columnsNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String fieldName = field.getKey();
            String fieldType = field.getValue().isObject() ? field.getValue().get("type").asText() : field.getValue().asText();
            classWriter.append("    private ").append(mapJsonTypeToJavaType(fieldType)).append(" ").append(fieldName).append(";").append(System.lineSeparator());
        }

        classWriter.append("}").append(System.lineSeparator());
        return classWriter.toString();
    }

    private static String mapJsonTypeToJavaType(String jsonType) {
        if (jsonType == null || jsonType.isEmpty()) {
            return "String";
        }
        ColumnType columnType = ColumnType.valueOf(jsonType.toUpperCase());
        switch (columnType) {
            case STRING:
                return "String";
            case INTEGER:
                return "Integer";
            case BOOLEAN:
                return "Boolean";
            case DATE:
                return "java.sql.Date";
            case TIMESTAMP:
                return "java.sql.Timestamp";
            default:
                throw new IllegalArgumentException("Unknown type: " + jsonType);
        }
    }
}
