package com.example.Humosoft.DTO.Request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SubTaskRequest {
	private String subTaskName;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer taskId;
}
