package com.example.Humosoft.DTO.Request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TimeSheetRequest {
    private String name;
    private Integer departmentId; //nếu id=0 sẽ tạo cho tất cả phòng ban
    private LocalDate startDate;  // Đảm bảo dùng LocalDate
    private LocalDate endDate;    // Đảm bảo dùng LocalDate
    private String type;
}
