package com.webflick.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.IdGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements java.io.Serializable{

    @Id
    Integer id;

    @Column
    private String name;
    @Column
    private int age;
    @Column
    private String address;

}

