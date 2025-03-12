package com.example.Humosoft.Service;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.TimeSheetRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Repository.AttendanceRepository;
import com.example.Humosoft.Repository.DepartmentRepository;
import com.example.Humosoft.Repository.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class AttendanceService {
	private final UserRepository userRepository;
	private  final AttendanceRepository attendanceRepository;
	private final DepartmentRepository departmentRepository;
	public void createAttendance(TimeSheetRequest timeSheetRequest) {
		Integer departmentId=timeSheetRequest.getDepartmentId();
		Department department = departmentRepository.findById(departmentId).orElseThrow(()->new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
	}
}
