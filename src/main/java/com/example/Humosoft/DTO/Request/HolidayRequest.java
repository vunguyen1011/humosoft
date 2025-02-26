package com.example.Humosoft.DTO.Request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data

public class HolidayRequest {
	private String name;
	private Date start;
	private Date end;
	private String description;
}
