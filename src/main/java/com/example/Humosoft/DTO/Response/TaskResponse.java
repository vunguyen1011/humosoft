package com.example.Humosoft.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {
    String taskName;
    String description;
    Date startDate;
    Date endDate;
    String status;
    String priority;
    boolean enabled= true;
}
