package com.example.Humosoft.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.Model.LeaveType;
import com.example.Humosoft.Service.LeaveTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave-types")
public class LeaveTypeController {

	private final LeaveTypeService leaveTypeService;
	@GetMapping
	Apiresponse<List<LeaveType>> getAllLeaveTypes() {
		return Apiresponse.<List<LeaveType>>builder()
				.code(200)
				.message("Success")
				.result(leaveTypeService.getAllLeaveTypes())
				.build();
	}
}
