package com.example.Humosoft.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.DepartmentMapper;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final DepartmentMapper departmentMapper;
	private final UserService userService;
	
	 public Department createDepartment(DepartmentRequest departmentRequest) {
		 	if(departmentRepository.existsByDepartmentName(departmentRequest.getDepartmentName()))
		 	{
		 		throw new WebErrorConfig(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
		 	}
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
	        return departmentRepository.findById(id).orElseThrow(()->new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
	        
	    }
	 public List<Department> getAll(){
		 return departmentRepository.findAll();
	 }
//	 public  void addEmployees(  User user,String deparment ) {
//		 Department department 
//	 }
	 
}
