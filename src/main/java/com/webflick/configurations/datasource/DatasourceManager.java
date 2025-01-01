package com.webflick.configurations.datasource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatasourceManager {

    public static final Map<String, JdbcTemplate> jdbcTemplateMap= new ConcurrentHashMap<>();
}
