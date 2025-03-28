package com.example.Humosoft.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Humosoft.Model.LeaveType;
import com.example.Humosoft.Repository.LeaveTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class LeaveTypeService {
 	
	private final LeaveTypeRepository leaveTypeRepository;
	
	
	public List<LeaveType> getAllLeaveTypes() {
		return leaveTypeRepository.findAll();
	}
}
	

