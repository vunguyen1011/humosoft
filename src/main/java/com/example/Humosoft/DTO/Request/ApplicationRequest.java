package com.example.Humosoft.DTO.Request;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter

public class ApplicationRequest {
	@NotBlank(message = "Title is required")
	private String title; // Tiêu đề công việc

	@NotBlank(message = "Description is required")
	private String description; // Mô tả công việc

	@NotNull(message = "Number of vacancies is required")
	@Min(value = 1, message = "Number of vacancies must be at least 1")
	private Integer numberOfVacancies; // Số lượng tuyển

	@NotNull(message = "Start date is required")
	@FutureOrPresent(message = "Start date must be today or in the future")
	private LocalDate startDate; // Ngày bắt đầu đăng tuyển

	@NotNull(message = "End date is required")
	@Future(message = "End date must be in the future")
	private LocalDate endDate; // Ngày kết thúc

	

	@NotNull(message = "Department ID is required")
	private Integer departmentId; // ID của phòng ban

	@NotNull(message = "Position ID is required")
	private Integer positionId; // ID của vị trí
	
}
