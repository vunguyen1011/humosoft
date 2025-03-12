package com.example.Humosoft.DTO.Request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TimeSheetRequest {
    private String name;
    private Integer departmentId;
    private LocalDate startDate;  // Đảm bảo dùng LocalDate
    private LocalDate endDate;    // Đảm bảo dùng LocalDate
    private String type;
}
