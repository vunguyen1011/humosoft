package com.example.Humosoft.DTO.Response;



import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;




@Data
@Builder
public class AttendanceResponse {
    private int id;
    private int userId;          // ID của người dùng
    private String fullName;      // Tên người dùng
    private String name;
 
    private String type;
    private LocalDate date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String status;
    private double totalHours;
    private boolean isDeleted;
    private String notes;
}
