package com.example.Humosoft.Service;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Response.SalaryResponse;
import com.example.Humosoft.DTO.Response.TimeOffResponse;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Mapper.SalaryMapper;
import com.example.Humosoft.Model.Salary;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Office.ExcelExporter;
import com.example.Humosoft.Repository.SalaryRepository;
import com.example.Humosoft.Repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class SalaryService {
	private final ExcelExporter excelExporter;
	private final AttendanceService attendanceService;
	private final TimeOffService timeOffService;
	private final HolidayService holidayService;
	private final SalaryMapper salaryMapper;
	private final UserService userService;
	private final SalaryRepository salaryRepository;
	 private Integer numberdayWorked(Integer userId, Integer month, Integer year) {
		 return attendanceService.getDaysWithCheckInAndCheckOut(userId, month, year).size();
		 
	 }
//	 private Integer numberdayTimeOff(Integer userId, Integer month, Integer year) {
//		 List<TimeOffResponse> timeOffs = timeOffService.getTimeOffRequestsByUser(userId);
//		 return timeOffs.stream().filter(timeOff -> timeOff.getStartDate().getMonthValue() == month && timeOff.getStartDate().getYear() == year).mapToInt(timeOff -> timeOff.getTotalDays()).sum();	
//	 }	
	 private double bonusMoney(Integer userId, Integer month, Integer year) {
		 return attendanceService.checkSunday(attendanceService.getDaysWithCheckInAndCheckOut(userId, month, year)).size()*200000.0;
		 
	 }
	 private Map<String, Integer> getPaidAndUnpaidLeaveDays(Integer userId, Integer month, Integer year) {
		    List<TimeOffResponse> timeOffs = timeOffService.getTimeOffRequestsByUser(userId);

		    int paidLeaveDays = timeOffs.stream()
		        .filter(timeOff -> timeOff.getStartDate().getMonthValue() == month 
		                        && timeOff.getStartDate().getYear() == year
		                        && timeOff.isPaid()) // Chỉ lấy nghỉ phép có lương
		        .mapToInt(TimeOffResponse::getTotalDays)
		        .sum();

		    int unpaidLeaveDays = timeOffs.stream()
		        .filter(timeOff -> timeOff.getStartDate().getMonthValue() == month 
		                        && timeOff.getStartDate().getYear() == year
		                        && !timeOff.isPaid()) // Chỉ lấy nghỉ phép không lương
		        .mapToInt(TimeOffResponse::getTotalDays)
		        .sum();

		    // Trả về kết quả dưới dạng Map
		    Map<String, Integer> leaveDays = new HashMap<>();
		    leaveDays.put("paidLeaveDays", paidLeaveDays);
		    leaveDays.put("unpaidLeaveDays", unpaidLeaveDays);

		    return leaveDays;
		}
	 private double basicSalary(Integer userId) {
		  User user = userService.findUerById(userId);
		  return user.getPosition().getPaygrade().getBaseSalary();
		}
	 
	 public SalaryResponse calculateSalaryForUser(Integer userId, Integer month, Integer year) {
		    Salary salary = new Salary();
		    User user = userService.findUerById(userId);
		    salary.setUser(user);
		    
		    // 1️⃣ Lấy lương cơ bản
		    double basicSalary = basicSalary(userId);
		    salary.setBasicSalary(basicSalary);
		    
		    // 2️⃣ Lấy tiền thưởng (làm Chủ Nhật)
		    double bonusMoney = bonusMoney(userId, month, year);
		    salary.setBonuses(bonusMoney);
		    
		    // 3️⃣ Số ngày nghỉ lễ trong tháng
		    int numberHoliday = holidayService.countHolidayDaysInMonth(month, year);
		    
		    // 4️⃣ Số ngày làm việc thực tế
		    int numberdayWorked = numberdayWorked(userId, month, year);
		    
		    // 5️⃣ Lấy số ngày nghỉ có/không lương
		    Map<String, Integer> leaveDays = getPaidAndUnpaidLeaveDays(userId, month, year);
		    int unpaidLeaveDays = leaveDays.getOrDefault("unpaidLeaveDays", 0);
		    int paidLeaveDays = leaveDays.getOrDefault("paidLeaveDays", 0);

		    // 6️⃣ Tính lương tổng (Gross Salary)
		    double dailySalary = basicSalary / 26; // Giả sử lương tính theo 26 ngày công chuẩn
		    double gross = (numberdayWorked + paidLeaveDays + numberHoliday) * dailySalary
		                        + bonusMoney
		                        - (unpaidLeaveDays * dailySalary);
		    salary.setGrossSalary(gross);
		    double deductions = gross * 0.03; // Trừ 10% thuế
		    salary.setDeductions(deductions);
		    double netSalary = gross - deductions;
		    salary.setNetSalary(netSalary);
		    salary.setMonth(month);
		    salary.setYear(year);
		    salaryRepository.save(salary);
		    return  salaryMapper.toSalaryResponse(salary);
	 }
		    
	 public List<SalaryResponse> calculateSalaryForAllUsers(Integer month, Integer year) {
		 List<UserResponse> users = userService.getAll();
		 return users.stream().map(user -> calculateSalaryForUser(user.getId(), month, year)).toList();
	 }
	 public List<SalaryResponse> getAllSalaries() {
		 List<Salary> salaries = salaryRepository.findAll();
		 return salaries.stream().map(salaryMapper::toSalaryResponse).toList();
	 }
	 public SalaryResponse getSalaryById(Integer id) {
		 Salary salary = salaryRepository.findById(id).orElseThrow(() -> new RuntimeException("Salary not found"));
		 return salaryMapper.toSalaryResponse(salary); 
	 }
	public List<SalaryResponse> getSalaryMonthAndYear(Integer month, Integer year) {
		List<Salary> salaries = salaryRepository.findByMonthAndYear(month, year);
		return salaries.stream().map(salaryMapper::toSalaryResponse).toList();
	}
	public List<SalaryResponse> getSalaryByUserFullNameInMonthAndYear(String fullName, Integer month, Integer year) {
		List<SalaryResponse>salarie = getSalaryMonthAndYear(month, year);
		return salarie.stream().filter(salary -> salary.getFullname().toLowerCase().contains(fullName.toLowerCase())).toList();
	}
	public List<SalaryResponse> getSalary(String departmentName, String fullname, String email, String number, int month, int year) {
	    List<Salary> salaries = salaryRepository.findSalaries(departmentName, fullname, email, number, month, year);
	    return salaries.stream().map(salaryMapper::toSalaryResponse).toList();
	}
	public void exportExcel(HttpServletResponse response,List<SalaryResponse> salaries) throws IOException {
		excelExporter.exportSalaryList(response, salaries);
	}
	

}


