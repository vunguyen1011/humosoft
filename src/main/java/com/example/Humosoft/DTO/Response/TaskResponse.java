package com.example.Humosoft.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {
	Integer id;
    String taskName;
    String description;
    LocalDate startDate;
    LocalDate endDate;	
    String status;
    String priority;
    List<String> departmentName;
    boolean enabled= true;
}
