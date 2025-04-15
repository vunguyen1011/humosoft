package com.example.Humosoft.DTO.Response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class InterviewResponse {
    private Integer id;
    private String recruitmentName;
    private String interviewerName;
    private Date interviewDate;
    private String location;
    private String status;
    private String notes;
    private String result;
    private Date createdAt;
    private Date updatedAt;

    public InterviewResponse() {
    }

}
