package com.example.Humosoft.DTO.Response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data

@NoArgsConstructor
public class InterviewResponse {
    private Integer id;
    private String recruitmentName;//tên ứng viên
    private String interviewerName;// ten người phỏng vấn
    private Date interviewDate;
    private String location;
    private String status;
    private String notes;
    private String result;
    private Date createdAt;
    private Date updatedAt;

}
