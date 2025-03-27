package com.example.Humosoft.Model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String departmentName;
    private String description;
    private Integer managerId;
    private Integer employees=0;
    private Boolean deleted=false;
    @ManyToMany(mappedBy = "departments",fetch = FetchType.EAGER)
    private Set<Task> tasks;
    

    // Getters and Setters
}