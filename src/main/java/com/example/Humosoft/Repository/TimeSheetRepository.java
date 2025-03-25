package com.example.Humosoft.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Humosoft.Model.Timesheet;
@Repository
public interface TimeSheetRepository  extends JpaRepository<Timesheet, Integer>{
	boolean existsByDepartmentIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
		    Integer departmentId,  LocalDate startDate, LocalDate endDate
		);
	boolean existsByName(String name);
	
	boolean existsByDepartmentIdAndStartDateBeforeAndEndDateAfter(
		    Integer departmentId, LocalDate endDate, LocalDate startDate
		);
	Optional<Timesheet> findByName(String name);
	
	
}
