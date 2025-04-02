package com.example.Humosoft.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.TimeSheetRequest;
import com.example.Humosoft.DTO.Request.TimeSheetRequestForCompany;
import com.example.Humosoft.DTO.Response.AttendanceResponse;
import com.example.Humosoft.DTO.Response.DepartmentResponse;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

	public List<TimeSheetResponse> createTimesheetForCompany(TimeSheetRequestForCompany timeSheetRequest){
		List<DepartmentResponse> departments = departmentService.getAll();
		List<TimeSheetResponse> timeSheetResponses = new ArrayList<>();

		for (DepartmentResponse department : departments) {

			TimeSheetRequest request = new TimeSheetRequest();
			request.setDepartmentId(department.getId());
			request.setStartDate(timeSheetRequest.getStartDate());
			request.setEndDate(timeSheetRequest.getEndDate());
			request.setName(timeSheetRequest.getName());
			

			TimeSheetResponse response = createTimesheetForDepartment(request);
			timeSheetResponses.add(response);
		}

		return timeSheetResponses;
	}

	public TimeSheetResponse createTimesheetForDepartment(TimeSheetRequest timeSheetRequest) {
		Department department = departmentService.getDepartmentEntityById(timeSheetRequest.getDepartmentId());
		LocalDate start = timeSheetRequest.getStartDate();
		LocalDate end = timeSheetRequest.getEndDate();

		boolean isTimesheetExists = timesheetRepository
				.existsByDepartmentIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(department.getId(), end,
						start);

		if (isTimesheetExists) {
			throw new WebErrorConfig(ErrorCode.TIMESHEET_DATE_OVERLAP);
		}

		Timesheet timesheet = timeSheetMapper.toTimeSheet(timeSheetRequest);
		timesheet.setDepartment(department);
		timesheetRepository.save(timesheet);

		List<UserResponse> users = departmentService.findUserInDepartment(department.getDepartmentName());
		if (!users.isEmpty()) {
			List<Attendance> attendances = users.stream()
					.map(userResponse -> userService.findUerById(userResponse.getId())).filter(Objects::nonNull)
					.flatMap(user -> createAttendanceForUser(user, start, end).stream())
					.filter(attendance -> !isAttendanceExists(attendance.getUser(), attendance.getDate()))
					.collect(Collectors.toList());

			attendanceRepository.saveAll(attendances);
		}

		return timeSheetMapper.toResponse(timesheet);
	}

	private List<Attendance> createAttendanceForUser(User user, LocalDate start, LocalDate end) {
		List<Attendance> attendances = new ArrayList<>();

		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			// Kiểm tra xem ngày đó đã tồn tại trong attendance chưa
			boolean isAttendanceExists = attendanceRepository.existsByUserAndDate(user, date);
			if (!isAttendanceExists) {
				Attendance attendance = new Attendance();
				attendance.setUser(user);
				attendance.setDate(date);
				attendance.setStatus("PENDING");
				attendances.add(attendance);
			}
		}

		return attendances;
	}

	private boolean isDepartmentTimesheetOverlap(Department department, LocalDate start, LocalDate end) {
		boolean isTimesheetExists = timesheetRepository
			    .existsByDepartmentIdAndStartDateBeforeAndEndDateAfter(department.getId(), end.plusDays(1), start.minusDays(1));
		return isTimesheetExists;

	}

	public boolean isAttendanceExists(User user, LocalDate date) {
		return attendanceRepository.existsByUserAndDate(user, date);
	}

	public List<TimeSheetResponse> getAll() {
		return timesheetRepository.findAll().stream().map(timeSheetMapper::toResponse).collect(Collectors.toList());
	}


	private List<AttendanceResponse> filterAttendanceByDateOrMonth(List<AttendanceResponse> attendances, LocalDate date,
			Integer month,Integer year) {
		if (date != null) {
			return attendances.stream().filter(att -> att.getDate().equals(date)) // Không cần chuyển đổi
					.collect(Collectors.toList());
		}
	    if (year != null && month == null) { // Nếu chỉ có năm (không có tháng)
	        return attendances.stream()
	                .filter(att -> att.getDate().getYear() == year)
	                .collect(Collectors.toList());
	    }

	    if (year != null && month != null) { // Nếu có cả tháng và năm
	        return attendances.stream()
	                .filter(att -> att.getDate().getYear() == year && att.getDate().getMonthValue() == month)
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

		Attendance attendance = attendanceRepository.findByUserAndDate(user, today)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTENDANCE_NOT_FOUND));

		if (attendance.getCheckIn() != null) {
			throw new WebErrorConfig(ErrorCode.ALREADY_CHECKED_IN);
		}

		attendance.setCheckIn(LocalDateTime.now());
		attendance.setStatus("Online");
		attendanceRepository.save(attendance);

		return attendanceMapper.toResponse(attendance);

	}

	public AttendanceResponse checkOut(Integer userId) {
		User user = userService.findUerById(userId);
		if (user == null) {
			throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
		}

		LocalDate today = LocalDate.now();

		Attendance attendance = attendanceRepository.findByUserAndDate(user, today)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.ATTENDANCE_NOT_FOUND));

		if (attendance.getCheckOut() != null) {
			throw new WebErrorConfig(ErrorCode.ALREADY_CHECKED_OUT);
		}

		LocalDateTime now = LocalDateTime.now();
		attendance.setCheckOut(now);
		attendance.setStatus("Completed");
		attendanceRepository.save(attendance);
		double totalHours = java.time.Duration.between(attendance.getCheckIn(), now).toHours();
		attendance.setTotalHours(totalHours);

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

    public List<AttendanceResponse> getAttendanceByTimesheetName(String timesheetName) {
        Timesheet timesheet = timesheetRepository.findByName(timesheetName)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.TIMESHEET_NOT_FOUND));

        List<Attendance> attendances = attendanceRepository.findAllByDepartmentAndDateRange(
                timesheet.getDepartment().getId(), timesheet.getStartDate(), timesheet.getEndDate());

        return attendances.stream()
                .map(attendanceMapper::toResponse)
                .collect(Collectors.toList());
    }
    public List<AttendanceResponse> filterAttendance(String timeSheetName,LocalDate date,
			Integer month,Integer year){
    	Timesheet timeSheet = timesheetRepository.findByName(timeSheetName)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.TIMESHEET_NOT_FOUND));
		List<AttendanceResponse> attendances = getAttendanceByTimesheetName(timeSheetName);
		 return filterAttendanceByDateOrMonth(attendances,date,month,year);

		
    }
    public List<Attendance> getDaysWithCheckInAndCheckOut(Integer userId, Integer month,Integer year) {
		
     User user = userService.findUerById(userId);
        if (user == null) {
         throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
    }

        List<Attendance> attendances = attendanceRepository.findByUserAndMonthAndYear(user, month,year);
     return attendances.stream()
              .filter(att -> att.getCheckIn() != null && att.getCheckOut() != null)
              .collect(Collectors.toList());
   }
    public List<Attendance> checkSunday(List<Attendance> attendances) {
		return attendances.stream()
				.filter(att -> att.getDate().getDayOfWeek().equals(DayOfWeek.SUNDAY))
				.collect(Collectors.toList());
	}
}


