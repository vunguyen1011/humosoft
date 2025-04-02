package com.example.Humosoft.Repository;

import java.lang.foreign.Linker.Option;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Humosoft.Model.Attendance;
import com.example.Humosoft.Model.Timesheet;
import com.example.Humosoft.Model.User;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
	boolean existsByUserAndDate(User user,LocalDate date);
	List<Attendance> findAllByUserIn(List<User> users);
	Optional<Attendance> findByUserAndDate(User user,LocalDate date);
    @Query("SELECT a FROM Attendance a WHERE a.user.department.id = :departmentId AND a.date BETWEEN :startDate AND :endDate")
    List<Attendance> findAllByDepartmentAndDateRange(@Param("departmentId") Integer departmentId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//	List<Attendance> findAllByTimesheet(Timesheet timesheet);
    @Query("SELECT a FROM Attendance a WHERE a.user = :user AND MONTH(a.date) = :month AND YEAR(a.date) = :year")
    List<Attendance> findByUserAndMonthAndYear(@Param("user") User user, @Param("month") int month, @Param("year") int year);
    

	

}
