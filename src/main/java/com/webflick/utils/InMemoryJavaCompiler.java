package com.webflick.utils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.tools.StandardJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InMemoryJavaCompiler {

    private static final Map<String, byte[]> byteCodeMap = new HashMap<>();

    public static Class<?> compile(String className, String sourceCode) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        JavaFileObject javaFileObject = new InMemoryJavaFileObject(className, sourceCode);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObject);

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        if (!task.call()) {
            throw new RuntimeException("Compilation failed.");
        }

        fileManager.close();
        InMemoryClassLoader classLoader = new InMemoryClassLoader();
        return ClassLoader.getSystemClassLoader().loadClass(className);
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

        public void setByteCode(String className, byte[] byteCode) {
            byteCodeMap.put(className, byteCode);
        }

        public static byte[] getByteCode(String className) {
            return byteCodeMap.get(className);
        }
    }

    private static class InMemoryClassLoader extends ClassLoader {
//        @Override
//        protected Class<?> findClass(String name) throws ClassNotFoundException {
//            byte[] byteCode = InMemoryJavaFileObject.getByteCode(name);
//            if (byteCode == null) {
//                throw new ClassNotFoundException(name);
//            }
//            return defineClass(name, byteCode, 0, byteCode.length);
//        }
    }
}