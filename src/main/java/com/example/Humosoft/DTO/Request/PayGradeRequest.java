package com.example.Humosoft.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayGradeRequest {
	private String paygradeName;
	private Integer baseSalary;
	private String description;
}
