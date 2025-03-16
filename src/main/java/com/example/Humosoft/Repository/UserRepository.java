package com.example.Humosoft.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
@Repository
public interface UserRepository  extends  JpaRepository<User,Integer>{
	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
	Optional<User> findByEmail(String email);
	List<User> findByDepartment(Department department);
	 List<User> findByDeletedFalse();
	  List<User> findByDepartmentAndDeletedFalse(Department department);
	
	  @Query("SELECT u FROM User u WHERE " +
	           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :request, '%')) " +
	           "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :request, '%')) " +
	           "OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :request, '%')) " +
	           "OR LOWER(u.department.departmentName) LIKE LOWER(CONCAT('%', :request, '%'))")
	    List<User> findByFullNameOrEmailOrPhoneOrDepartment(@Param("request") String request);
	@Query("SELECT DISTINCT u FROM User u JOIN u.role r WHERE r.name = :roleName AND u.deleted = false")
	List<User> findByRoleName(@Param("roleName") String roleName);
	 boolean existsByEmail(String email);
	    boolean existsByPhone(String phone);
	    

}
