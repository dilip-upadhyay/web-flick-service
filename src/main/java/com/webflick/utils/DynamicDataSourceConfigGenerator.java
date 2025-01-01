package com.webflick.utils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Map;

import com.webflick.dao.DatabaseDriver;

@Slf4j
@Component
public class DynamicDataSourceConfigGenerator {

    public String generateDataSourceConfig(Map<String, String> dataSourceProperties) throws Exception {
        String classStr = """
                package com.webflick.configurations;
                
                import javax.sql.DataSource;
                
                import org.springframework.beans.factory.annotation.Qualifier;
                import org.springframework.boot.jdbc.DataSourceBuilder;
                import org.springframework.context.annotation.Bean;
                import org.springframework.context.annotation.Configuration;
                import org.springframework.context.annotation.Primary;
                import org.springframework.jdbc.core.JdbcTemplate;
                import com.webflick.configurations.datasource.DatasourceManager;
                
                @Configuration
                public class DataSourceConfig {
                
                    //@Primary
                    @Bean
                    public DataSource getInMemoryDataSource() {
                        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
                        dataSourceBuilder.driverClassName("org.h2.Driver");
                        dataSourceBuilder.url("jdbc:h2:mem:testdb");
                        dataSourceBuilder.username("dbUsername");
                        dataSourceBuilder.password("dbPassword");
                        return dataSourceBuilder.build();
                    }
                
                    @Bean
                    public JdbcTemplate h2JdbcTemplate(@Qualifier("getInMemoryDataSource") DataSource dataSource) {
                         JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                         DatasourceManager.jdbcTemplateMap.put("h2JdbcTemplate", jdbcTemplate);
                         return jdbcTemplate;
                    }
                }
                
                """;
        String className = Utils.firstCharToUpperRemoveSpaces(dataSourceProperties.get("datasourceName")) + "DataSourceConfig";
        classStr = classStr.replace("getInMemoryDataSource", "get" + dataSourceProperties.get("datasourceName") + "DataSource")
                .replace("public class DataSourceConfig", "public class " + className)
                .replace("org.h2.Driver", DatabaseDriver.getDriverClassNameByDatabaseName(dataSourceProperties.get("database")))
                .replace("h2JdbcTemplate", dataSourceProperties.get("datasourceName") + "JdbcTemplate")
                .replace("dbUsername", dataSourceProperties.get("username"))
                .replace("dbPassword", dataSourceProperties.get("password"))
                .replace("h2:mem:testdb", DatabaseDriver.getUrlPrefixByDatabaseName(dataSourceProperties.get("database")) + dataSourceProperties.get("hostname") + ":" + dataSourceProperties.get("port") + "/" + dataSourceProperties.get("databaseName"));

        log.info("DataSourceConfig class generated: {}", classStr);
        JavaClassGenerator.generateClass(className, classStr);
        return classStr;
    }
}
