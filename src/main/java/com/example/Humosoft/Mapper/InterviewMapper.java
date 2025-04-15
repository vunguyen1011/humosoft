package com.example.Humosoft.Mapper;

import com.example.Humosoft.DTO.Response.InterviewResponse;
import com.example.Humosoft.Model.Interview;
import org.springframework.stereotype.Component;


@Component
public class InterviewMapper {

    public InterviewResponse toInterviewResponse(Interview interview) {
        if (interview != null) {
            return null;
        }
        InterviewResponse interviewResponse = new InterviewResponse();
        interviewResponse.setId(interview.getId());
        interviewResponse.setInterviewDate(interview.getInterviewDate());
        interviewResponse.setLocation(interview.getLocation());
        interviewResponse.setStatus(interview.getStatus());
        interviewResponse.setNotes(interview.getNotes());
        interviewResponse.setResult(interview.getResult());
        interviewResponse.setCreatedAt(interview.getCreatedAt());
        interviewResponse.setUpdatedAt(interview.getUpdatedAt());
        return interviewResponse;
    }

}
