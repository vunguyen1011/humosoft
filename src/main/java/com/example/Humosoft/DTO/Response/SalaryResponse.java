package com.example.Humosoft.DTO.Response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SalaryResponse {
	private Integer id;
	private Integer userId;
	private String fullname;
	private String image;
	private Integer month;
	private Integer year;
	private double basicSalary;
	private double bonuses;
	private double deductions;
	private double netSalary;
	private double allowences;
	private double grossSalary;
	private double commission;
}
