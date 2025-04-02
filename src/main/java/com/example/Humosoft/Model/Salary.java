package com.example.Humosoft.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Salary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "paygrade_id")
	private Paygrade paygrade;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id")  // Đảm bảo có cột khóa ngoại trỏ đến User
    private User user;
	private Integer month;
	private Integer year;
	private double basicSalary;
	private double bonuses;
	private double deductions;
	private double netSalary;
	private double allowences;	
	private double grossSalary;
	private double commission;
	
	private boolean status;

	// Getters and Setters
}
