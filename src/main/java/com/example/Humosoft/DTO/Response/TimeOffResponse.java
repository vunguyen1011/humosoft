package com.example.Humosoft.DTO.Response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeOffResponse {
	 private Integer id;
	    private String username;       // Tên người dùng
	    private String leaveTypeName;  // Tên loại nghỉ phép
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private String reason;
	    private String status;
	    private int totalDays;         
	    private LocalDate createdAt;
}
