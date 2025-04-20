package com.example.Humosoft.Mapper;

import com.example.Humosoft.DTO.Request.InterviewRequest;
import com.example.Humosoft.DTO.Response.InterviewResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.Interview;
import com.example.Humosoft.Model.Recruitment;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.RecruitmentRepository;
import com.example.Humosoft.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InterviewMapper {
	  private final UserRepository userRepository;
	     private final RecruitmentRepository recruitmentRepository;
	public Interview toInterview(InterviewRequest interviewRequest) {
		
	    User user = userRepository.findById(interviewRequest.getInterviewer())
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

        Recruitment recruitment = recruitmentRepository.findById(interviewRequest.getRecruitment())
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.RECRUITMENT_NOT_FOUND));

        recruitment.setStatus("INTERVIEWING");
        recruitmentRepository.save(recruitment);
	    Interview interview = new Interview();
	    interview.setInterviewDate(interviewRequest.getInterviewDate());
	    interview.setLocation(interviewRequest.getLocation());
	   interview.setInterviewer(interviewRequest.getInterviewer());
	   interview.setRecruitment(interviewRequest.getRecruitment());
	    interview.setNotes(interviewRequest.getNotes());

	    return interview;
	}

	public InterviewResponse toInterviewResponse(Interview interview) {
	    if (interview == null) {
	        return null;
	    }

	    User user = userRepository.findById(interview.getInterviewer())
	            .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

	    Recruitment recruitment = recruitmentRepository.findById(interview.getRecruitment())
	            .orElseThrow(() -> new WebErrorConfig(ErrorCode.RECRUITMENT_NOT_FOUND));

	    return InterviewResponse.builder()
	            .id(interview.getId())
	            .interviewDate(interview.getInterviewDate())
	            .location(interview.getLocation())

	            .notes(interview.getNotes())
	            .recruitmentName(recruitment)
	            .interviewerName(user.getFullName())
	            .createdAt(interview.getCreatedAt())
	            .build();
	}



}
