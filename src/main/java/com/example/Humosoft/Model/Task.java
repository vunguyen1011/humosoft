package com.example.Humosoft.Model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String taskName;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status;
    private String priority;

    // Getters and Setters
}
