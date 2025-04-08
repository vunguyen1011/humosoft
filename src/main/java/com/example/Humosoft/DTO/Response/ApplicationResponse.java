package com.example.Humosoft.DTO.Response;

import java.time.LocalDate;

import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.Position;

import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApplicationResponse {
	private int id;

	private String title;
	private String description;
	
	private int numberOfVacancies; 
	private LocalDate startDate;
	private LocalDate endDate; 
	private String status; 
	private String  departmentName;
	private String positionName;
}
