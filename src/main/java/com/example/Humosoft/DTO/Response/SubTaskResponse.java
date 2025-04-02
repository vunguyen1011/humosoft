package com.example.Humosoft.DTO.Response;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubTaskResponse {
	private Integer id;
	private String subTaskName;
	List<String> fullName;
	private LocalDate startDate;
	private LocalDate endDate;

	private String taskName;
	private String status;
}
