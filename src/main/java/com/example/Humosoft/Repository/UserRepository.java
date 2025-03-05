package com.example.Humosoft.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
@Repository
public interface UserRepository  extends  JpaRepository<User,Integer>{
	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
	Optional<User> findByEmail(String email);
	List<User> findByDepartment(Department department);
	
	List<User> findByFullNameOrEmail(String fullName,String email);
}
