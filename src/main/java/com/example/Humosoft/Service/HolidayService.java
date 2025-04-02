package com.example.Humosoft.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.HolidayRequest;
import com.example.Humosoft.Mapper.HolidayMapper;
import com.example.Humosoft.Model.Holiday;
import com.example.Humosoft.Repository.HoilidayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HolidayService {
	public  final HoilidayRepository holidayRepository;
	public final HolidayMapper holidayMapper;
	public Holiday create(HolidayRequest request) {
		Holiday holiday=holidayMapper.toHoliday(request);
		holidayRepository.save(holiday);
		return holiday;
	}
	public List<Holiday> getAll(){
		return holidayRepository.findAll();
	}
	public void delete (Integer Id) {
		holidayRepository.deleteById(Id);
	}
	public Integer countHolidayDaysInMonth(Integer month, Integer year) {
	    List<Holiday> holidays = holidayRepository.getHolidaysInMonth(month, year);
	    Integer totalDays = 0;

	    for (Holiday holiday : holidays) {
	        LocalDate start = holiday.getStart();
	        LocalDate end = holiday.getEnd();

	        while (!start.isAfter(end)) { // Lặp từng ngày từ start đến end
	            if (start.getMonthValue() == month && start.getYear() == year) {
	                totalDays++; // Đếm ngày thuộc tháng cần tìm
	            }
	            start = start.plusDays(1);
	        }
	    }
	    return totalDays;
	}


}
