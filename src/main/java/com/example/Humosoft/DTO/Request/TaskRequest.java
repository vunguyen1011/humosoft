package com.example.Humosoft.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequest {
    String taskName;
    String description;
    Date startDate;
    Date endDate;
    String status;
    String priority;
    boolean enabled= true;
}
