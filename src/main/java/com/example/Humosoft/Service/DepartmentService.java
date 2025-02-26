package com.example.Humosoft.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.Mapper.DepartmentMapper;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final DepartmentMapper departmentMapper;
	 public Department createDepartment(DepartmentRequest departmentRequest) {
	        Department department = departmentMapper.toDepartment(departmentRequest);
	        return departmentRepository.save(department);
	    }
	 public Department updateDepartment(Integer id, DepartmentRequest departmentRequest) {
	        Optional<Department> existingDepartmentOpt = departmentRepository.findById(id);
	        if (existingDepartmentOpt.isPresent()) {
	            Department existingDepartment = existingDepartmentOpt.get();
	            Department updatedDepartment = departmentMapper.toDepartment(departmentRequest);
	            updatedDepartment.setId(existingDepartment.getId());  // Keep the same ID
	            return departmentRepository.save(updatedDepartment);
	        }
	        return null;  // Or throw an exception if department is not found
	    }
	 public Department getDepartmentById(Integer id) {
	        return departmentRepository.findById(id).orElseThrow(()->new RuntimeException("PHong ban nay khong ton tai"));
	        
	    }
	 public List<Department> getAll(){
		 return departmentRepository.findAll();
	 }
}
