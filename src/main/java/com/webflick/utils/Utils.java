package com.webflick.utils;

import com.webflick.WebFlickApplication;
import com.webflick.configurations.datasource.DatasourceManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class Utils {
    public static String toCamelCase(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    public String readFileToString(String path) {
        String strJson = null;
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            byte[] binaryData = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            strJson = new String(binaryData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            classPathResource = null;
        }
        return strJson;
    }

    public static String firstCharToUpperRemoveSpaces(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).replaceAll("[^a-zA-Z]", "");

    }

    public static void restart(ConfigurableApplicationContext context) {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        context.close();
        context = SpringApplication.run(WebFlickApplication.class, args.getSourceArgs());

    }

    public static JdbcTemplate getJdbcTemplate(String jdbcTemplateName) {
        return DatasourceManager.jdbcTemplateMap.get(jdbcTemplateName);
    }
}
