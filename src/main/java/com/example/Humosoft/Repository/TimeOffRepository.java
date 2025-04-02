package com.example.Humosoft.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Humosoft.Model.TimeOff;

import java.time.LocalDate;
import java.util.List;
public interface TimeOffRepository extends JpaRepository<TimeOff, Integer> {

    @Query("SELECT t FROM TimeOff t WHERE t.user.id = :userId " +
           "AND (:startDate BETWEEN t.startDate AND t.endDate " +
           "OR :endDate BETWEEN t.startDate AND t.endDate " +
           "OR (t.startDate BETWEEN :startDate AND :endDate))")
    List<TimeOff> findOverlappingRequests(
        @Param("userId") Integer userId, 
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
    @Query("SELECT t FROM TimeOff t WHERE t.user.department.departmentName = :departmentName")
    List<TimeOff> findByDepartmentName(@Param("departmentName") String departmentName);
    @Query("SELECT t FROM TimeOff t WHERE t.user.department.departmentName = :departmentName " +
    	       "AND t.user.id <> (SELECT d.managerId FROM Department d WHERE d.departmentName = :departmentName)")
    	List<TimeOff> findByDepartmentNameExcludingManager(@Param("departmentName") String departmentName);
    List<TimeOff> findByUser_Id(Integer userId);


}