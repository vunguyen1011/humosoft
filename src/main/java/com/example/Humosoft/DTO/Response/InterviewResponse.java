package com.example.Humosoft.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewResponse {
    private Integer id;
    private String recruitmentName;//tên ứng viên
    private String interviewerName;// ten người phỏng vấn
    private Date interviewDate;
    private String location;
    private String notes;
    private Date createdAt;
    private Date updatedAt;

}
