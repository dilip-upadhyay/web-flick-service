package com.webflick.utils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.tools.StandardJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class JavaClassGenerator {

    public static void generateClass(String className, String sourceCode, String outputDir) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        JavaFileObject javaFileObject = new InMemoryJavaFileObject(className, sourceCode);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObject);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, Arrays.asList("-d", outputDir), null, compilationUnits);
        if (!task.call()) {
            throw new RuntimeException("Compilation failed.");
        }
        fileManager.close();
    }

    private static class InMemoryJavaFileObject extends SimpleJavaFileObject {
        private final String sourceCode;
        protected InMemoryJavaFileObject(String className, String sourceCode) {
            super(URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension), Kind.SOURCE);
            this.sourceCode = sourceCode;
        }
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return sourceCode;
        }
    }
}