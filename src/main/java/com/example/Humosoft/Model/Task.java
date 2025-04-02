package com.example.Humosoft.Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String taskName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String priority;
    @ManyToMany
    @JoinTable(
        name = "department_task", 
        joinColumns = @JoinColumn(name = "task_id"), 
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @ToString.Exclude
    @JsonManagedReference 
    private Set<Department>departments=new HashSet<>();
    private boolean enabled= true;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude // Ngăn vòng lặp vô hạn
    @JsonIgnore
    private Set<SubTask> subTasks;

    // Getters and Setters
}
