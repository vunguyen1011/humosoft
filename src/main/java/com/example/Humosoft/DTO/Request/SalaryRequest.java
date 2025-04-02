package com.example.Humosoft.DTO.Request;

import java.time.LocalDate;
import java.util.Date;

import com.example.Humosoft.Model.User;

import lombok.Data;

@Data
public class SalaryRequest {
	private Integer userId;
	 private Integer month;
	private Integer year;
}
