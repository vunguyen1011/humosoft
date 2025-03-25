package com.example.Humosoft.Mapper;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.DTO.Response.DepartmentResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class DepartmentMapper {
	public final UserRepository userRepository;
	
	public Department toDepartment(DepartmentRequest request) {
	
Department department = new Department();
//        if(!userRepository.existsById(request.getManagerId())) throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
        // Setting the properties of the Department object from DepartmentRequest
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());

        return department;
	}
	public DepartmentResponse toResponse(Department department) {
	    User manager = (department.getManagerId() != null) ? 
                userRepository.findById(department.getManagerId()).orElse(null) : 
                null;
		return  DepartmentResponse.builder()
				.id(department.getId())
				.departmentName(department.getDepartmentName())
				.description(department.getDescription())
				.managerId(manager!=null?manager.getId():null)
				.managerName(manager!=null?manager.getFullName():"N/A")
				.build();
	}
}
