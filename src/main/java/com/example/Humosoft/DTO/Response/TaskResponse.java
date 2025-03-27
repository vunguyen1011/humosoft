package com.example.Humosoft.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

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
    boolean enabled= true;
}
