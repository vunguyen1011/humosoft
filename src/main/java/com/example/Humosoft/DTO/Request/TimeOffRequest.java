package com.example.Humosoft.DTO.Request;

import java.time.LocalDate;

import lombok.Data;

@Data

public class TimeOffRequest {
		 private Integer userId;
		    private Integer leaveTypeId;
		    private LocalDate startDate;
		    private LocalDate endDate;
		    private String reason;
}
