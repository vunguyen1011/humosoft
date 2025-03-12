package com.example.Humosoft.Mapper;

import com.example.Humosoft.DTO.Response.AttendanceResponse;
import com.example.Humosoft.Model.Attendance;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {
    public AttendanceResponse toResponse(Attendance attendance) {
        return AttendanceResponse.builder()
                .id(attendance.getId())
                .userId(attendance.getUser().getId())
                .fullName(attendance.getUser().getFullName()) // Sử dụng fullName thay vì userName
                .name(attendance.getName())
                .type(attendance.getType())
                .date(attendance.getDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .status(attendance.getStatus())
                .totalHours(attendance.getTotalHours())
                .isDeleted(attendance.isDeleted())
                .notes(attendance.getNotes())
                .build();
    }
}
