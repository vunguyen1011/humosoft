package com.example.Humosoft.Mapper;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.TimeOffRequest;
import com.example.Humosoft.DTO.Response.TimeOffResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.LeaveType;
import com.example.Humosoft.Model.TimeOff;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.LeaveTypeRepository;
import com.example.Humosoft.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Component
public class TimeOffMapper {
	private final UserRepository userRepository;
	private final LeaveTypeRepository leaveTypeRepository;
	 public TimeOffResponse convertToResponse(TimeOff timeOff) {
	       return TimeOffResponse.builder()
	                .id(timeOff.getId())
	                .fullname(timeOff.getUser().getFullName())
	                .leaveTypeName(timeOff.getLeaveType().getLeaveTypeName())
	                .startDate(timeOff.getStartDate())
	                .endDate(timeOff.getEndDate())
	                .reason(timeOff.getReason())
	                .status(timeOff.getStatus())
	                .totalDays(timeOff.getTotalDays())
	                .createdAt(timeOff.getCreatedAt())
	                .isPaid(timeOff.getLeaveType().isPaid())
	                .build(); 	
	    } 
	 
	 public TimeOff convertToEntity(TimeOffRequest requestDTO) {
	        TimeOff timeOff = new TimeOff();
	        User user = userRepository.findById(requestDTO.getUserId()).orElseThrow(()->new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
	        LeaveType leaveType=leaveTypeRepository.findById(requestDTO.getLeaveTypeId()).orElseThrow(()->new WebErrorConfig(ErrorCode.LEAVE_TYPE_NOT_FOUND));
	        timeOff.setUser(user);
	        timeOff.setLeaveType(leaveType);
	        timeOff.setStartDate(requestDTO.getStartDate());
	        timeOff.setEndDate(requestDTO.getEndDate());
	        timeOff.setReason(requestDTO.getReason());
	        timeOff.setStatus("Pending");
	        timeOff.setTotalDays(requestDTO.getEndDate().getDayOfYear() - requestDTO.getStartDate().getDayOfYear()); // Tính số ngày nghỉ
	        timeOff.setCreatedAt(java.time.LocalDate.now());
	        return timeOff;
	    }

}
