package com.example.Humosoft.Mapper;

import org.springframework.stereotype.Component;
import com.example.Humosoft.DTO.Request.TimeSheetRequest;
import com.example.Humosoft.DTO.Response.TimeSheetResponse;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.Timesheet;
import com.example.Humosoft.Repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TimeSheetMapper {
    
    private final DepartmentRepository departmentRepository;

    // Chuyển từ Timesheet sang TimeSheetResponse
    public TimeSheetResponse toResponse(Timesheet timesheet) {
        if (timesheet == null) {
            return null;
        }

        TimeSheetResponse response = new TimeSheetResponse();
        response.setId(timesheet.getId());
        response.setName(timesheet.getName());
        response.setBranch(timesheet.getBranch());
        response.setStartDate(timesheet.getStartDate()); // java.sql.Date
        response.setEndDate(timesheet.getEndDate());     // java.sql.Date
        response.setType(timesheet.getType());
        response.setStatus(timesheet.getStatus());
        response.setDepartmentName(timesheet.getDepartment().getDepartmentName());  
        return response;
    }

    // Chuyển từ TimeSheetRequest sang Timesheet entity
    public Timesheet toTimeSheet(TimeSheetRequest timeSheetRequest) {
        if (timeSheetRequest == null) {
            return null;
        }

        Department department = departmentRepository.findById(timeSheetRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Timesheet timesheet = new Timesheet();
        timesheet.setDepartment(department);
        timesheet.setName(timeSheetRequest.getName()+" for "+department.getDepartmentName());
        timesheet.setStartDate(timeSheetRequest.getStartDate());
        timesheet.setEndDate(timeSheetRequest.getEndDate());
        timesheet.setType(timeSheetRequest.getType());
        timesheet.setStatus("Active");

        return timesheet;
    }
}
