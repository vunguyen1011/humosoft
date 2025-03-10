package com.example.Humosoft.Mapper;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.Department;
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
}
