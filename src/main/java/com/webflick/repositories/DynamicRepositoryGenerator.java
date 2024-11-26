package com.webflick.repositories;

import com.webflick.models.Employee;
import jakarta.persistence.EntityManager;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.stereotype.Repository;

public class DynamicRepositoryGenerator {

    public static Object generate(EntityManager entityManager) throws Exception {
        TypeDescription.Generic genericType = TypeDescription.Generic.Builder
                .parameterizedType(JpaRepository.class, Employee.class, Long.class)
                .build();

        AnnotationDescription repositoryAnnotation = AnnotationDescription.Builder.ofType(Repository.class).build();

        Class<?> dynamicRepository = new ByteBuddy()
                .makeInterface(genericType)
                .name("com.webflick.repositories.EmployeeRepository")
                .annotateType(repositoryAnnotation)
                //.defineMethod("findAll", List.class, Modifier.ABSTRACT)
                //.intercept(FixedValue.value(Arrays.asList(new Employee(1, "Name", 25, "Address"))))
                //.defineMethod("findById", Employee.class, Modifier.PUBLIC)
                .make()
                .load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
        //ClassLoader classLoader = dynamicRepository.getClassLoader();
        //classLoader.loadClass(dynamicRepository.getName());

       // ClassLoader.getPlatformClassLoader().loadClass(dynamicRepository.getName());

        System.out.println("Dynamic Repository Interface: " + dynamicRepository.getName());
        RepositoryFactorySupport factory = new JpaRepositoryFactory(entityManager);
        Object repository = factory.getRepository(dynamicRepository);


        return dynamicRepository.getDeclaredConstructor().newInstance();
    }
}