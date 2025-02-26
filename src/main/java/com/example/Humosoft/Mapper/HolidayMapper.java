package com.example.Humosoft.Mapper;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.HolidayRequest;
import com.example.Humosoft.Model.Holiday;

import lombok.Builder;

@Builder
@Component
public class HolidayMapper {
	public Holiday toHoliday(HolidayRequest holidayRequest) {
		Holiday holiday =new Holiday();
		holiday.setName(holidayRequest.getName());
		holiday.setDescription(holidayRequest.getDescription());
		holiday.setStart(holidayRequest.getStart());
		holiday.setEnd(holidayRequest.getEnd());
		return holiday;
	}
}
