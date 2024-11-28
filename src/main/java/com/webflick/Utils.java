package com.webflick;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
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

    public String readFile(String path) throws FileNotFoundException {
        //getClass().getResource(path);
        File file = ResourceUtils.getFile(path);
        String content = null;
        try {
             content = Files.readString(Path.of(file.getPath()));
            System.out.println(content);
        } catch (IOException e) {
            log.error("Error while reading file{} :: {}" , file.getPath(), e.getLocalizedMessage());
        }
        return content;
    }

    public String readFileToString(String path) {
        String strJson = null;
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            byte[] binaryData = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            strJson = new String(binaryData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strJson;
    }
}
