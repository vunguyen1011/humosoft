package com.example.Humosoft.Mapper;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.Model.Department;

import lombok.Data;

@Component
@Data
public class DepartmentMapper {
	public Department toDepartment(DepartmentRequest request) {
Department department = new Department();
        
        // Setting the properties of the Department object from DepartmentRequest
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());

        return department;
	}
}
