package com.example.Humosoft.DTO.Request;

import java.util.Date;

import lombok.Data;
@Data
public class InterviewRequest {
	 private Integer recruitment;
	    private Integer interviewer;
	    private Date interviewDate;
	    private String location;
	    private String notes;
	

}
