package com.example.Humosoft.DTO.Response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TimeSheetResponse {
    private Integer id;
    private String name;
    private String branch;
    private LocalDate startDate;  // ✅ Dùng LocalDate
    private LocalDate endDate;    // ✅ Dùng LocalDate
    private String type;
    private String status;
    private String departmentName;
}

