package com.webflick.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class H2DAO {

    private final JdbcTemplate h2JdbcTemplate;

    @jakarta.annotation.PostConstruct
    public void getTables() {
        try {
            DatabaseMetaData metaData = Objects.requireNonNull(h2JdbcTemplate.getDataSource()).getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE", "VIEW"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                String tableType = tables.getString("TABLE_TYPE");
                log.info("Table/View Name: {} - Type: {}", tableName, tableType);
            }
        } catch (SQLException e) {
            log.error("Error retrieving tables", e);
        }
    }

    public String createTable(String tableName, Map<String, Object> columns) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(tableName).append(" (");
        for (Map.Entry<String, Object> column : columns.entrySet()) {
            String columnName = column.getKey();
            Object columnTypeObj = column.getValue();
            String columnType;

            columnType = switch (columnTypeObj) {
                case ColumnType type -> type.getSqlType();
                case Map<?, ?> typeMap -> {
                    ColumnType type = (ColumnType) typeMap.get("type");
                    Integer length = (Integer) typeMap.get("length");
                    yield length != null ? type.getSqlTypeWithLength(length) : type.getSqlType();
                }
                default -> "VARCHAR(100)";
            };

            sql.append(columnName).append(" ").append(columnType).append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(");");
        h2JdbcTemplate.execute(sql.toString());
        return sql.toString();
    }
}
