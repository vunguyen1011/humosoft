package com.example.Humosoft.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.TimeSheetRequest;
import com.example.Humosoft.DTO.Request.TimeSheetRequestForCompany;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.AttendanceResponse;
import com.example.Humosoft.DTO.Response.TimeSheetResponse;
import com.example.Humosoft.Model.Attendance;
import com.example.Humosoft.Service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendances")
public class AttendanceController {
	private final AttendanceService attendanceService;
	@PostMapping("/company")
	public Apiresponse<List<TimeSheetResponse>> createTimeSheet(
	       @RequestBody TimeSheetRequestForCompany request) {
	    
	    List<TimeSheetResponse> timeSheetResponses = attendanceService.createTimesheetForCompany(request);
	    
	    return Apiresponse.<List<TimeSheetResponse>>builder()
	            .result(timeSheetResponses)
	            .build();
	}
	

	@PostMapping("/department")
	public Apiresponse<TimeSheetResponse> createTimeSheetForDepartment(
	        @RequestBody TimeSheetRequest timeSheetRequest) {

	    TimeSheetResponse timeSheetResponse = attendanceService.createTimesheetForDepartment(timeSheetRequest);

	    return Apiresponse.<TimeSheetResponse>builder()
	            .result(timeSheetResponse)
	            .build();
	}

	@GetMapping
	public Apiresponse<List<TimeSheetResponse>> getAll(){
		List<TimeSheetResponse> timeSheetResponses=attendanceService.getAll();
		return Apiresponse.<List<TimeSheetResponse>>builder()
				.result(timeSheetResponses)
				.build();
	}
	@GetMapping("/filter")
	public Apiresponse<List<AttendanceResponse>> getAttendanceByDepartment(
	        @RequestParam String timesheetName,
	        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
	        @RequestParam(required = false) Integer month, 
	        @RequestParam(required = false) Integer year  
	) {
	    List<AttendanceResponse> attendances = attendanceService.filterAttendance(timesheetName, date, month,year);
	    return Apiresponse.<List<AttendanceResponse>>builder()
	            .result(attendances)
	            .build();
	}
	  @PostMapping("/check-in/{userId}")
	    public Apiresponse<AttendanceResponse> checkIn(@PathVariable Integer userId) {
	        AttendanceResponse response = attendanceService.checkIn(userId);
	        return Apiresponse.<AttendanceResponse>builder()
	                .result(response)
	                .build();
	    }

	    // API Check-out
	    @PostMapping("/check-out/{userId}")
	    public Apiresponse<AttendanceResponse> checkOut(@PathVariable Integer userId) {
	        AttendanceResponse response = attendanceService.checkOut(userId);
	        return Apiresponse.<AttendanceResponse>builder()
	                .result(response)
	                .build();
	    }
	    @GetMapping("/find")
	    public Apiresponse<AttendanceResponse> getAttendanceOfUser(
	            @RequestParam Integer userId,
	            @RequestParam LocalDate date) {
	        
	        AttendanceResponse response = attendanceService.getAttendanceByUserAndDate(userId, date);
	        
	        return Apiresponse.<AttendanceResponse>builder()
	                .result(response)
	                .build();
	    }
	    @GetMapping("/attendance-detail")
	    public Apiresponse<List<AttendanceResponse>> getAttendanceByTimesheetName(@RequestParam String timesheetName){
	    	List<AttendanceResponse> attendances=attendanceService.getAttendanceByTimesheetName(timesheetName);
	    	return Apiresponse.<List<AttendanceResponse>>builder()
	    			.result(attendances)
	    			.build();	
	    }
	       
	
}