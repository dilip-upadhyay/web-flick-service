package com.webflick;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
}
