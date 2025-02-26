package com.example.Humosoft.Service;

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
}
