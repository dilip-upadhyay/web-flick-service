package com.webflick.controllers;

import com.webflick.models.Employee;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
//@RestController(value = "/employee")
public class DynamicControllerGenerator {

    public static Object main1(String[] args) throws Exception {
        AnnotationDescription restControllerAnnotation = AnnotationDescription.Builder.ofType(RestController.class).build();
        AnnotationDescription requestMappingAnnotation = AnnotationDescription.Builder.ofType(RequestMapping.class).build();
        AnnotationDescription getMappingAnnotation = AnnotationDescription.Builder.ofType(GetMapping.class).build();
        AnnotationDescription postMappingAnnotation = AnnotationDescription.Builder.ofType(PostMapping.class).build();

        Class<?> dynamicController = new ByteBuddy()
                .subclass(Object.class)
                .name("com.webflick.controllers.EmployeeController1")
                .annotateType(List.of(restControllerAnnotation, requestMappingAnnotation))
                .defineField("employees", List.class, Modifier.PRIVATE)
                //.value(new ArrayList<>())
                .defineMethod("getEmployee", List.class, Modifier.PUBLIC)
                .intercept(FixedValue.value(new ArrayList<>(List.of(new Employee(2,"Name2", 26, "Address")))))
                .annotateMethod(getMappingAnnotation)
                .defineMethod("addEmployee", void.class, Modifier.PUBLIC)
                .withParameter(Employee.class)
                .intercept(MethodDelegation.to(new Object() {
                    @SuppressWarnings("unused")
                    public void addEmployee(Employee employee) {
                        // Add employee to the list
                    }
                }))
                .annotateMethod(postMappingAnnotation)
                .make()
                .load(DynamicControllerGenerator.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        System.out.println("Dynamic Controller Class: " +  dynamicController.getName());


        return dynamicController.getDeclaredConstructor().newInstance();


    }
}