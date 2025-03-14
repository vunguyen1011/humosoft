package com.example.Humosoft.Model;

import jakarta.persistence.Column;
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
	@JoinColumn(name = "paygrade_id")
	private Paygrade paygrade;

    @ManyToOne
    @JoinColumn(name = "user_id")  // Đảm bảo có cột khóa ngoại trỏ đến User
    private User user;
	private Date salaryMonth;
	private int baseSalary;
	private int bonuses;
	private int deductions;
	private int netSalary;
	private boolean status;

	// Getters and Setters
}
