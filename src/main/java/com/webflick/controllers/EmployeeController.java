//package com.webflick.controllers;
//
//import com.webflick.models.Employee;
//import com.webflick.repositories.EmployeeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(path="/employees")
//@RequiredArgsConstructor
//public class EmployeeController {
//
//    private final EmployeeRepository employeeRepository;
//    @GetMapping
//    public List<Employee> getEmployee() {
//        //return employeeRepository.findAll();
//        return List.of(new Employee(1,"Name", 25, "Address"));
//    }
//
//
//}