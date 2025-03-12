package com.example.Humosoft.DTO.Request;

import java.sql.Date;

import lombok.Data;
@Data

public class TimeSheetRequest {
	private String name;
    private Date startDate;
    private Date endDate;
    private String type; // Daily/Hour
    private Integer departmentId;
}
