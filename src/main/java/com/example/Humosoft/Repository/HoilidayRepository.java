package com.example.Humosoft.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Humosoft.Model.Holiday;

public interface HoilidayRepository  extends  JpaRepository<Holiday, Integer>{
	@Query("SELECT h FROM Holiday h where month(h.start) = ?1 and year(h.start) = ?2")	
	List<Holiday> getHolidaysInMonth(Integer month, Integer year);
}
