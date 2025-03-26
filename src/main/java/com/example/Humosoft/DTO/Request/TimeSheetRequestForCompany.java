package com.example.Humosoft.DTO.Request;

import java.time.LocalDate;

import lombok.Data;


@Data

public class TimeSheetRequestForCompany {
	 private String name;
	    private LocalDate startDate;
	    private LocalDate endDate;
}
