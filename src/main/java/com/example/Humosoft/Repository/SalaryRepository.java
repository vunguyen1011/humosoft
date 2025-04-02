package com.example.Humosoft.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.Humosoft.Model.Salary;

/**
 * 
 */
@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer> {

	// Tìm lương theo tên nhân viên (không phân biệt hoa thường)
	List<Salary> findByUserFullNameContainingIgnoreCase(String fullName);

	@Query("SELECT s FROM Salary s JOIN s.user u JOIN u.department d " +
		       "WHERE (:departmentName IS NULL OR d.departmentName = :departmentName) " +
		       "OR (:fullName IS NULL OR u.fullName = :fullName) " +
		       "OR (:email IS NULL OR u.email = :email) " +
		       "OR (:phone IS NULL OR u.phone = :phone) " +
		       "OR (s.year = :year AND s.month = :month)")
		List<Salary> findSalaries(@Param("departmentName") String departmentName, 
		                          @Param("fullName") String fullName,
		                          @Param("email") String email, 
		                          @Param("phone") String phone, 
		                          @Param("month") int month,
		                          @Param("year") int year);

	// Tìm lương theo tháng và năm
	List<Salary> findByMonthAndYear(Integer month, Integer year);
}
