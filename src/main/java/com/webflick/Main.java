package com.webflick;

public class Main {
    public static void main1(String[] args) {
        String className = "com.example.HelloWorld";
        String sourceCode = """
            package com.example;
            public class HelloWorld {
                public void sayHello() {
                    System.out.println("Hello, World!");
                }
            }
            """;
        String outputDir = "target/classes";

        try {
            JavaClassGenerator.generateClass(className, sourceCode, outputDir);
            System.out.println("Class generated successfully.");
            Object o = ClassLoader.getSystemClassLoader().loadClass(className).getDeclaredConstructor().newInstance();
            o.getClass().getMethod("sayHello").invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}