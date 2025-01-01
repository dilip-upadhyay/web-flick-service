//package com.webflick;
//
//import com.webflick.utils.JavaClassGenerator;
//import com.webflick.utils.Utils;
//import jakarta.persistence.EntityManager;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.stereotype.Component;
//
//@Component
//@AllArgsConstructor
//public class StartRunner implements ApplicationRunner {
//
//    /* Add whatever Bean you need here and autowire them through the constructor or with @Autowired */
//    @Autowired
//    EntityManager entityManager;
//    @Autowired
//    ConfigurableApplicationContext configurableApplicationContext;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        //generateRepos();
//        //generateControllers();
//        //restart(configurableApplicationContext);
//        System.out.println("Application started");
//        //loadDynamicRepositories(configurableApplicationContext);
//        // Do whatever you need here inside
//    }
//    private static void generateRepos() throws Exception {
//        String className = "com.webflick.repositories.EmployeeRepository";
//        //compile();
//        String repoClass = new Utils().readFileToString("templates/repository-class.tmp");
//        repoClass = repoClass.replace("RepositoryName", "EmployeeRepository");
//        repoClass = repoClass.replace("EntityName", "Employee");
//        String outputDir = "target/classes";
//        System.out.println(repoClass);
//        JavaClassGenerator.generateClass(className, repoClass);
//    }
//
//    private static void generateControllers() throws Exception {
//        createEmployeeController();
//        createEmployeeeController();
//    }
//
//    private static void createEmployeeController() throws Exception {
//        String pathName = "employees";
//        String className = "com.webflick.controllers.EmployeeController";
//        //compile();
//        String repoClass = new Utils().readFileToString("templates/controller-class.tmp");
//        repoClass = repoClass.replace("ControllerName", "EmployeeController");
//        repoClass = repoClass.replace("pathName", pathName);
//        repoClass = repoClass.replace("RepositoryName", "EmployeeRepository");
//        String outputDir = "target/classes";
//        System.out.println(repoClass);
//        JavaClassGenerator.generateClass(className, repoClass);
//    }
//
//    private static void createEmployeeeController() throws Exception {
//        String pathName = "employeees";
//        String className = "com.webflick.controllers.EmployeeeController";
//        //compile();
//        String repoClass = new Utils().readFileToString("templates/controller-class.tmp");
//        repoClass = repoClass.replace("ControllerName", "EmployeeeController");
//        repoClass = repoClass.replace("pathName", pathName);
//        repoClass = repoClass.replace("RepositoryName", "EmployeeRepository");
//        String outputDir = "target/classes";
//        System.out.println(repoClass);
//        JavaClassGenerator.generateClass(className, repoClass);
//    }
//
//
//
////    private void loadDynamicRepositories(ConfigurableApplicationContext configurableApplicationContext) throws Exception {
////        EntityManager entityManager = ((EntityManagerFactory) configurableApplicationContext.getBean("entityManagerFactory")).createEntityManager();
////        Object singletonObject = DynamicRepositoryGenerator.generate(entityManager);
////        String className = singletonObject.getClass().getName();
////        String beanName = toCamelCase(className.substring(className.lastIndexOf(".") + 1));
////        configurableApplicationContext.getBeanFactory().registerSingleton(beanName, singletonObject);
////    }
//
//
//}
