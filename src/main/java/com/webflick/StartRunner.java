package com.webflick;

import com.webflick.repositories.DynamicRepositoryGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartRunner implements ApplicationRunner {

    /* Add whatever Bean you need here and autowire them through the constructor or with @Autowired */
    @Autowired
    EntityManager entityManager;
    @Autowired
    ConfigurableApplicationContext configurableApplicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Application started");
        //loadDynamicRepositories(configurableApplicationContext);
        // Do whatever you need here inside
    }

    private void loadDynamicRepositories(ConfigurableApplicationContext configurableApplicationContext) throws Exception {
        EntityManager entityManager = ((EntityManagerFactory) configurableApplicationContext.getBean("entityManagerFactory")).createEntityManager();
        Object singletonObject = DynamicRepositoryGenerator.generate(entityManager);
        String className = singletonObject.getClass().getName();
        String beanName = toCamelCase(className.substring(className.lastIndexOf(".") + 1));
        configurableApplicationContext.getBeanFactory().registerSingleton(beanName, singletonObject);
    }

    private static String toCamelCase(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

}