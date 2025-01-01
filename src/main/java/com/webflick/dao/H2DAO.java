package com.webflick.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflick.configurations.datasource.DatasourceManager;
import com.webflick.utils.DynamicDataSourceConfigGenerator;
import com.webflick.utils.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class H2DAO {

    private final DynamicDataSourceConfigGenerator dataSourceConfigGenerator;

    public String createDataSource(Map<String, String> dataSourceProperties) {
        try {
            String classStr = dataSourceConfigGenerator.generateDataSourceConfig(dataSourceProperties);
            log.info("DataSource created successfully");
            return classStr;
        }
        catch (Exception e) {
            log.error("Error creating datasource", e);
            throw new RuntimeException(e);
        }
    }

    // @jakarta.annotation.PostConstruct
    public void getTables(String jdbcTemplateName) {
        try {
            DatabaseMetaData metaData = Objects.requireNonNull(Utils.getJdbcTemplate(jdbcTemplateName + "JdbcTemplate").getDataSource()).getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{ "TABLE", "VIEW" });
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                String tableType = tables.getString("TABLE_TYPE");
                log.info("Table/View Name: {} - Type: {}", tableName, tableType);
            }
        }
        catch (SQLException e) {
            log.error("Error retrieving tables", e);
        }
    }
    public List<?> getAll(String sql, String datasourceName) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Utils.getJdbcTemplate(datasourceName + "JdbcTemplate").query(sql, (rs) -> {
            Map<String, Object> rowMap = new HashMap<>();
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                rowMap.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
            }
            resultList.add(rowMap);
        });
        return resultList;
    }
    public String createTable(String jsonString) {
        log.info("CREATE_TABLE jsonString: {}", jsonString);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonString);
            String tableName = rootNode.get("tableName").asText();
            String datasourceName = rootNode.get("datasourceName").asText();
            JsonNode columnsNode = rootNode.get("columns");

            StringBuilder sql = new StringBuilder("CREATE TABLE ").append(tableName).append(" (");
            Iterator<Map.Entry<String, JsonNode>> fields = columnsNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String columnName = field.getKey();
                JsonNode columnDetails = field.getValue();
                String columnType = columnDetails.isObject() ? columnDetails.get("type").asText() : columnDetails.asText();
                String columnTypeSql = mapJsonTypeToSqlType(columnType, columnDetails);

                sql.append(columnName).append(" ").append(columnTypeSql).append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(");");

            log.info("CREATE_TABLE generated Script: {}", sql.toString());
            Utils.getJdbcTemplate(datasourceName + "JdbcTemplate").execute(sql.toString());
            return sql.toString();
        }
        catch (Exception e) {
            log.error("Error creating table", e);
            throw new RuntimeException(e);
        }
    }

    private String mapJsonTypeToSqlType(String jsonType, JsonNode columnDetails) {
        if (jsonType == null || jsonType.isEmpty()) {
            return "VARCHAR(255)";
        }
        ColumnType columnType = ColumnType.valueOf(jsonType.toUpperCase());
        switch (columnType) {
            case STRING:
                int length = columnDetails.has("length") ? columnDetails.get("length").asInt() : 255;
                return "VARCHAR(" + length + ")";
            case INTEGER:
                return "INTEGER";
            case BOOLEAN:
                return "BOOLEAN";
            case DATE:
                return "DATE";
            case TIMESTAMP:
                return "TIMESTAMP";
            default:
                throw new IllegalArgumentException("Unknown type: " + jsonType);
        }
    }
}
