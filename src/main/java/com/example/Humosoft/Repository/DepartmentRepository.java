package com.example.Humosoft.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Humosoft.Model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{
Optional<Department> findByDepartmentName(String departmentName);
boolean existsByDepartmentName(String departmentName);
boolean existsByManagerId(Integer managerId);
}
