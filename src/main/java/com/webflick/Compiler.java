package com.webflick;

import com.google.common.reflect.ClassPath;
import org.springframework.boot.SpringApplication;

import java.io.File;
import java.io.IOException;
import javax.tools.*;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Compiler {
    public static void compileTestClass() throws IOException {
        String className = "com.webflick.repositories.EmployeeRepository";
        //compile();
        String repoClass = new Utils().readFileToString("templates/repository-class.tmp");
        repoClass = repoClass.replace("RepositoryName","EmployeeRepository");
        repoClass = repoClass.replace("EntityName","Employee");
        String outputDir = "target/classes";
        Compiler.compile(className, repoClass, new File("target/classes"));
    }
    public static void compile(String className, String javaSource, File outputDir) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        //JavaCompiler.class.ClassLoader().getResource("templates/repository-class.tmp");
        ClassPath classPath = ClassPath.from(WebFlickApplication.class.getClassLoader());
        Set<ClassPath.ClassInfo> classes = classPath.getAllClasses();
        classes = classes.stream().filter(e -> !e.getName().contains("-"))
                .filter(e -> !e.getName().contains("h2.")).collect(Collectors.toSet());
        List<String> collectedClasses = classes.stream().map(e -> e.getName()+".class").collect(Collectors.toList());
        Iterable<String> collectedClassesIterable = Collections.unmodifiableList(collectedClasses);
        outputDir.mkdirs();
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singleton(outputDir));
        //fileManager.setLocation(StandardLocation.CLASS_PATH, List.of(new File("")));

        JavaSourceFromString javaSourceFromString = new JavaSourceFromString(className, javaSource);
        Iterable<? extends JavaFileObject> compilationUnits = Collections.singletonList(javaSourceFromString);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        boolean success = task.call();
        if (!success) {
            throw new RuntimeException("Compilation failed.");
        }
    }
}

/**
 * A file object used to represent source coming from a string.
 */
class JavaSourceFromString extends SimpleJavaFileObject {
    /**
     * The source code of this "file".
     */
    final String code;

    /**
     * Constructs a new JavaSourceFromString.
     *
     * @param name the name of the compilation unit represented by this file object
     * @param code the source code for the compilation unit represented by this file object
     */
    JavaSourceFromString(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}
