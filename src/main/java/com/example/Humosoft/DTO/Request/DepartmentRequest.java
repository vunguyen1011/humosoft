package com.example.Humosoft.DTO.Request;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class DepartmentRequest {
	private String departmentName;
	private String description;
	
	
}
