package com.example.Humosoft.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.TimeOffRequest;
import com.example.Humosoft.DTO.Response.TimeOffResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.TimeOffMapper;
import com.example.Humosoft.Model.LeaveType;
import com.example.Humosoft.Model.TimeOff;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.LeaveTypeRepository;
import com.example.Humosoft.Repository.TimeOffRepository;
import com.example.Humosoft.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeOffService {
	
	    private final TimeOffRepository timeOffRepository;
	    private final TimeOffMapper timeOffMapper;
	   
	    private final UserRepository userRepository;

	    
	    private final LeaveTypeRepository leaveTypeRepository;

	    public void submitTimeOffRequest(TimeOffRequest requestDTO) {
	        // Kiểm tra xem user có tồn tại không
	        User user = userRepository.findById(requestDTO.getUserId())
	                .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

	        // Kiểm tra loại nghỉ phép có tồn tại không
	        LeaveType leaveType = leaveTypeRepository.findById(requestDTO.getLeaveTypeId())
	                .orElseThrow(() -> new WebErrorConfig(ErrorCode.LEAVE_TYPE_NOT_FOUND));

	        // Kiểm tra ngày hợp lệ
	        LocalDate startDate = requestDTO.getStartDate();
	        LocalDate endDate = requestDTO.getEndDate();
	        if (endDate.isBefore(startDate)) {
	            throw new WebErrorConfig(ErrorCode.INVALID_DATE_RANGE);
	        }

	        // Kiểm tra xem có đơn nghỉ phép nào trùng ngày không
	        List<TimeOff> overlappingRequests = timeOffRepository.findOverlappingRequests(user.getId(), startDate, endDate);
	        if (!overlappingRequests.isEmpty()) {
	            throw new WebErrorConfig(ErrorCode.TIMEOFF_OVERLAP);
	        }

	        // Lưu đơn nghỉ phép vào database
	        TimeOff timeOff = timeOffMapper.convertToEntity(requestDTO);
	        

	        timeOffRepository.save(timeOff);
	    }
	    public List<TimeOffResponse> getAllTimeOffRequests() {
	        List<TimeOff> timeOffList = timeOffRepository.findAll();
	        return timeOffList.stream()
	                .map(timeOffMapper::convertToResponse) // Gọi phương thức từ Mapper
	                .collect(Collectors.toList());
	    }
	    public List<TimeOffResponse> getTimeOffRequestsByDepartment(String departmentname) {
	        List<TimeOff> timeOffList = timeOffRepository.findByDepartmentName(departmentname);
	        return timeOffList.stream()
	                .map(timeOffMapper::convertToResponse)
	                .collect(Collectors.toList());
	    }
	    public void approveTimeOff(Integer timeOffId) {
	        TimeOff timeOff = timeOffRepository.findById(timeOffId)
	                .orElseThrow(() -> new WebErrorConfig(ErrorCode.TIMEOFF_NOT_FOUND));
	        timeOff.setStatus("Approved");
	        timeOffRepository.save(timeOff);
	    }

	    public void rejectTimeOff(Integer timeOffId) {
	        TimeOff timeOff = timeOffRepository.findById(timeOffId)
	                .orElseThrow(() -> new WebErrorConfig(ErrorCode.TIMEOFF_NOT_FOUND));
	        timeOff.setStatus("Rejected");
	        timeOffRepository.save(timeOff);
	    }
	    public List<TimeOffResponse> getTimeOffRequestsByDepartmentExcludingManager(String departmentName) {
	        List<TimeOff> timeOffRequests = timeOffRepository.findByDepartmentNameExcludingManager(departmentName);

	        return timeOffRequests.stream()
	                .map(timeOffMapper::convertToResponse)
	                .collect(Collectors.toList());
	    }
	    public  List<TimeOffResponse> getTimeOffRequestsByUser(Integer userId) {
	        List<TimeOff> timeOffRequests = timeOffRepository.findByUser_Id(userId);

	        return timeOffRequests.stream()
	                .map(timeOffMapper::convertToResponse)
	                .collect(Collectors.toList());
	    }
	    



}
