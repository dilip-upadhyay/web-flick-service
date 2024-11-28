package com.webflick;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class WebFlickApplication {



	public static void main(String[] args) throws Exception {


		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WebFlickApplication.class, args);
		//System.getProperty("user.library.path");
		generateRepos();
		generateControllers();
		//Compiler.compileTestClass();
		restart(configurableApplicationContext);
	}

	public static void restart( ConfigurableApplicationContext context) {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		context.close();
		context = SpringApplication.run(WebFlickApplication.class, args.getSourceArgs());



	}

	private static void generateRepos() throws Exception {
		String className = "com.webflick.repositories.EmployeeRepository";
		//compile();
		String repoClass = new Utils().readFileToString("templates/repository-class.tmp");
		repoClass = repoClass.replace("RepositoryName","EmployeeRepository");
		repoClass = repoClass.replace("EntityName","Employee");
		String outputDir = "target/classes";
		System.out.println(repoClass);
		JavaClassGenerator.generateClass(className, repoClass, outputDir);
	}

	private static void generateControllers() throws Exception {
		createEmployeeController();
		createEmployeeeController();
	}

	private static void createEmployeeController() throws Exception {
		String pathName = "employees";
		String className = "com.webflick.controllers.EmployeeController";
		//compile();
		String repoClass = new Utils().readFileToString("templates/controller-class.tmp");
		repoClass = repoClass.replace("ControllerName","EmployeeController");
		repoClass = repoClass.replace("pathName",pathName);
		repoClass = repoClass.replace("RepositoryName","EmployeeRepository");
		String outputDir = "target/classes";
		System.out.println(repoClass);
		JavaClassGenerator.generateClass(className, repoClass, outputDir);
	}

	private static void createEmployeeeController() throws Exception {
		String pathName = "employeees";
		String className = "com.webflick.controllers.EmployeeeController";
		//compile();
		String repoClass = new Utils().readFileToString("templates/controller-class.tmp");
		repoClass = repoClass.replace("ControllerName","EmployeeeController");
		repoClass = repoClass.replace("pathName",pathName);
		repoClass = repoClass.replace("RepositoryName","EmployeeRepository");
		String outputDir = "target/classes";
		System.out.println(repoClass);
		JavaClassGenerator.generateClass(className, repoClass, outputDir);
	}


//	private static void loadDynamicRepositories(ConfigurableApplicationContext configurableApplicationContext) throws Exception {
//		EntityManager entityManager = ((EntityManagerFactory)configurableApplicationContext.getBean("entityManagerFactory")).createEntityManager();
//		Object singletonObject = DynamicRepositoryGenerator.generate(entityManager);
//		String className = singletonObject.getClass().getName();
//		String beanName = toCamelCase(className.substring(className.lastIndexOf(".") + 1));
//		configurableApplicationContext.getBeanFactory().registerSingleton(beanName, singletonObject);
//	}
//
//	private static void loadDynamicControllers(ConfigurableApplicationContext configurableApplicationContext) throws Exception {
//		Object singletonObject = DynamicControllerGenerator.main1(null);
//		String className = singletonObject.getClass().getName();
//		String beanName = toCamelCase(className.substring(className.lastIndexOf(".") + 1));
//		configurableApplicationContext.getBeanFactory().registerSingleton(beanName, singletonObject);
////		System.out.println(configurableApplicationContext.getBeanFactory().getBean(beanName));
//
//		configurableApplicationContext.getBean("requestMappingHandlerMapping");
//		configurableApplicationContext.getBean("requestMappingHandlerMapping",RequestMappingHandlerMapping.class)
//				.registerMapping(
//						RequestMappingInfo.paths("/employees1")
//								.build(),
//						configurableApplicationContext.getBean("employeeController1", singletonObject.getClass()),
//						singletonObject.getClass().getMethod("getEmployee"));
//	}

//	private static String toCamelCase(String s){
//		return s.substring(0, 1).toLowerCase() + s.substring(1);
//	}

}
