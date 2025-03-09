package com.example.Humosoft.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;



@Entity
@Data
public class Paygrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String paygradeName;
    private Integer baseSalary;
    private String description;
    private Boolean deleted=false;

    // Getters and Setters
}
