package com.example.Humosoft.Repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Humosoft.Model.Timesheet;
@Repository
public interface TimeSheetRepository  extends JpaRepository<Timesheet, Integer>{
	boolean existsByDepartmentIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
		    Integer departmentId, LocalDate endDate, LocalDate startDate
		);
	boolean existsByName(String name);


}
