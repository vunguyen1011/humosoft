package com.example.Humosoft.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.SalaryRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.SalaryResponse;
import com.example.Humosoft.Service.SalaryService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RestController
@RequestMapping("/salarys")
@RequiredArgsConstructor
public class SalaryController {
	private final SalaryService salaryService;
	@PostMapping ("/caculate")
	public Apiresponse<SalaryResponse> getSalary(@RequestBody SalaryRequest salaryRequest) {
		return Apiresponse.<SalaryResponse>builder()
				.result(salaryService.calculateSalaryForUser(salaryRequest.getUserId(),salaryRequest.getMonth(),salaryRequest.getYear()))
				.build();
	
}
	@PostMapping ("/caculateAll")
	public Apiresponse<List<SalaryResponse>> getSalaryAll(@RequestParam int month, @RequestParam int year) {
		return Apiresponse.<List<SalaryResponse>>builder()
				.result(salaryService.calculateSalaryForAllUsers(month,year))
				.build();
	}
	@GetMapping
	public Apiresponse<List<SalaryResponse>> getAlSalarys() {
		return Apiresponse.<List<SalaryResponse>>builder()
				.result(salaryService.getAllSalaries())
				.build();
	}
	@GetMapping("/department")
	public List<SalaryResponse> getSalariesByDepartmentAndMonth(@RequestParam String departmentName, @RequestParam int month, @RequestParam int year) {
		return salaryService.getSalaryByUserFullNameInMonthAndYear(departmentName, month, year);
	}
	@GetMapping("/search")
	public Apiresponse<List<SalaryResponse>> searchSalaries(
	        @RequestParam(required = false) String departmentName, 
	        @RequestParam(required = false) String fullName, 
	        @RequestParam(required = false) String email, 
	        @RequestParam(required = false) String phone, 
	        @RequestParam int month,
	        @RequestParam int year) {
	    System.out.println("Params: month=" + month + ", year=" + year + ", dept=" + departmentName + 
	                       ", fullName=" + fullName + ", email=" + email + ", phone=" + phone);
	    List<SalaryResponse> result = salaryService.getSalary(departmentName, fullName, email, phone, month, year);
	    System.out.println("Result: " + result);
	    return Apiresponse.<List<SalaryResponse>>builder()
	            .result(result)
	            .build();
	}  
	
	@GetMapping("/export-salary")
	public void exportSalary(
	        HttpServletResponse response,
	        @RequestParam(required = false) String departmentName,
	        @RequestParam(required = false) String fullname,
	        @RequestParam(required = false) String email,
	        @RequestParam(required = false) String phone,
	        @RequestParam Integer month,
	        @RequestParam Integer year
	) throws IOException {
	    // Lấy danh sách SalaryResponse từ database
	    List<SalaryResponse> salaries = salaryService.getSalary(departmentName, fullname, email, phone, month, year);

	    // Gọi hàm export Excel
	    salaryService.exportExcel(response, salaries);
	}

	

}
