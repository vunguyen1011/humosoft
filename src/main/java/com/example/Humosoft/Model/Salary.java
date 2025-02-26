package com.example.Humosoft.Model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;





@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "paygrade_id")
    private Paygrade paygrade;

    private Date salaryMonth;
    private int baseSalary;
    private int bonuses;
    private int deductions;
    private int netSalary;
    private String status;

    // Getters and Setters
}
