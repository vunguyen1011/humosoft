package com.example.Humosoft.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.TimeSheetRequest;
import com.example.Humosoft.DTO.Response.AttendanceResponse;
import com.example.Humosoft.DTO.Response.TimeSheetResponse;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.AttendanceMapper;
import com.example.Humosoft.Mapper.TimeSheetMapper;
import com.example.Humosoft.Model.Attendance;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.Timesheet;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.AttendanceRepository;
import com.example.Humosoft.Repository.TimeSheetRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

    private final DepartmentService departmentService;
    private final UserService userService;
    private final TimeSheetRepository timesheetRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final TimeSheetMapper timeSheetMapper;

    public TimeSheetResponse createTimesheet(TimeSheetRequest timeSheetRequest) {
        LocalDate start = timeSheetRequest.getStartDate();
        LocalDate end = timeSheetRequest.getEndDate();

        if (timesheetRepository.existsByName(timeSheetRequest.getName())) {
            throw new WebErrorConfig(ErrorCode.TIMESHEET_ALREADY_EXISTS);
        }

        if (start.isAfter(end)) {
            throw new WebErrorConfig(ErrorCode.INVALID_DATE_RANGE);
        }

        if (ChronoUnit.DAYS.between(start, end) >= 31) {
            throw new WebErrorConfig(ErrorCode.EXCEEDS_ONE_MONTH_LIMIT);
        }

        Integer departmentId = timeSheetRequest.getDepartmentId();
        logger.info("Fetching department with ID: {}", departmentId);
        Department department = departmentService.getDepartmentById(departmentId);
        logger.info("Department found: {}", department.getDepartmentName());

        boolean isTimesheetExists = timesheetRepository.existsByDepartmentIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                departmentId, end, start);
        if (isTimesheetExists) {
            throw new WebErrorConfig(ErrorCode.TIMESHEET_DATE_OVERLAP);
        }

        Timesheet timesheet = timeSheetMapper.toTimeSheet(timeSheetRequest);
        timesheetRepository.save(timesheet);
        logger.info("Created timesheet: {}", timesheet.getName());

        List<UserResponse> users = departmentService.findUserInDepartment(department.getDepartmentName());
        if (users.isEmpty()) {
            logger.warn("No users found in department: {}", department.getDepartmentName());
            throw new WebErrorConfig(ErrorCode.NO_USERS_IN_DEPARTMENT);
        }

        List<Attendance> attendances = users.stream()
                .map(userResponse -> userService.findUerById(userResponse.getId()))
                .filter(user -> user != null)
                .flatMap(user -> createAttendanceForUser(user, start, end).stream())
                .filter(attendance -> !isAttendanceExists(attendance.getUser(), attendance.getDate()))
                .collect(Collectors.toList());

        logger.info("Saving {} attendance records to database...", attendances.size());
        attendanceRepository.saveAll(attendances);
        logger.info("Saved successfully.");

        return timeSheetMapper.toResponse(timesheet);
    }

    public List<Attendance> createAttendanceForUser(User user, LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1))
                .map(day -> {
                    Attendance attendance = new Attendance();
                    attendance.setUser(user);
                    attendance.setDate(day); // Giữ nguyên kiểu LocalDate
                    attendance.setStatus("Active");
                    attendance.setDeleted(false);
                    return attendance;
                }).collect(Collectors.toList());
    }


    public boolean isAttendanceExists(User user, LocalDate date) {
        return attendanceRepository.existsByUserAndDate(user, date);
    }
    
    public List<TimeSheetResponse> getAll() {
        return timesheetRepository.findAll()
                .stream()
                .map(timeSheetMapper::toResponse)
                .collect(Collectors.toList());
    }
    public List<AttendanceResponse> getAttendanceByDepartmentName(String departmentName, LocalDate date, Integer month) {
        logger.info("Fetching attendance records for department: {}", departmentName);

        Department department = departmentService.getDepartmentByName(departmentName);
        if (department == null) {
            throw new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND);
        }

        List<UserResponse> users = departmentService.findUserInDepartment(departmentName);
        if (users.isEmpty()) {
            logger.warn("No users found in department: {}", departmentName);
            return List.of();
        }

        List<User> userEntities = users.stream()
                .map(userResponse -> userService.findUerById(userResponse.getId()))
                .filter(user -> user != null)
                .collect(Collectors.toList());

        List<Attendance> attendances = attendanceRepository.findAllByUserIn(userEntities);

        // Chỉ lọc nếu người dùng nhập điều kiện lọc
        if (date != null || month != null) {
            attendances = filterAttendanceByDateOrMonth(attendances, date, month);
        }
        
        logger.info("Returning {} attendance records for department: {}", attendances.size(), departmentName);

        // Chuyển đổi sang AttendanceResponse
        return attendances.stream()
                .map(attendanceMapper::toResponse)
                .collect(Collectors.toList());
    }
    private List<Attendance> filterAttendanceByDateOrMonth(List<Attendance> attendances, LocalDate date, Integer month) {
        if (date != null) {
            return attendances.stream()
                    .filter(att -> att.getDate().equals(date)) // Không cần chuyển đổi
                    .collect(Collectors.toList());
        }
        if (month != null) {
            return attendances.stream()
                    .filter(att -> att.getDate().getMonthValue() == month) // Trực tiếp lấy tháng từ LocalDate
                    .collect(Collectors.toList());
        }
        return attendances;
    }

    public AttendanceResponse checkIn(Integer userId) {
        User user = userService.findUerById(userId);
        if (user == null) {
            throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
        }

        LocalDate today = LocalDate.now();
       

        Attendance attendance = attendanceRepository.findByUserAndDate(user, today).orElseThrow(()->new WebErrorConfig(ErrorCode.ATTENDANCE_NOT_FOUND));
        
      
       
        if (attendance.getCheckIn() != null) {
            throw new WebErrorConfig(ErrorCode.ALREADY_CHECKED_IN);
        }

        attendance.setCheckIn(LocalDateTime.now()); 
        attendance.setStatus("Checked-in");
        attendanceRepository.save(attendance);
        
        return attendanceMapper.toResponse(attendance);
        
    }
    public AttendanceResponse checkOut(Integer userId) {
        User user = userService.findUerById(userId);
        if (user == null) {
            throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
        }

        LocalDate today = LocalDate.now();
       

        Attendance attendance = attendanceRepository.findByUserAndDate(user, today).orElseThrow(()->new WebErrorConfig(ErrorCode.ATTENDANCE_NOT_FOUND));
        
      
       
        if (attendance.getCheckOut() != null) {
            throw new WebErrorConfig(ErrorCode.ALREADY_CHECKED_OUT);
        }

        attendance.setCheckOut(LocalDateTime.now()); 
        attendance.setStatus("Checked-out");
        attendanceRepository.save(attendance);
        
        return attendanceMapper.toResponse(attendance);
        
    }
    public AttendanceResponse getAttendanceByUserAndDate(Integer userId, LocalDate date) {
        User user = userService.findUerById(userId);
        if (user == null) {
            throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
        }

        Attendance attendance = attendanceRepository.findByUserAndDate(user, date)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTENDANCE_NOT_FOUND));

        return attendanceMapper.toResponse(attendance);
    }





}