package com.example.Humosoft.Service;

import com.example.Humosoft.DTO.Request.InterviewRequest;
import com.example.Humosoft.DTO.Response.InterviewResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.InterviewMapper;
import com.example.Humosoft.Model.Interview;
import com.example.Humosoft.Model.Recruitment;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.InterviewRepository;
import com.example.Humosoft.Repository.RecruitmentRepository;
import com.example.Humosoft.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InterviewService {
    InterviewRepository interviewRepository;
    InterviewMapper interviewMapper;
    UserRepository userRepository;
    RecruitmentRepository recruitmentRepository;

    public InterviewResponse createInterview(InterviewRequest interviewRequest) {

        Interview interview = interviewMapper.toInterview(interviewRequest);

        // ❗ Save trước để đảm bảo có ID và các field tự sinh
        interview = interviewRepository.save(interview);

      

		return interviewMapper.toInterviewResponse(interview);
    }

    public List<InterviewResponse> getAllInterviews() {
        List<Interview> interviews = interviewRepository.findAll();
        return interviews.stream()
                .map(interview -> interviewMapper.toInterviewResponse(interview))
                .collect(Collectors.toList());
    }

//
//    public InterviewResponse updateInterview(Integer id, Interview updatedInterview) {
//        if (!interviewRepository.existsById(id)) {
//            throw new WebErrorConfig(ErrorCode.INTERVIEW_NOT_FOUND);
//        }
//        
//        updatedInterview.setId(id);
//        interviewRepository.save(updatedInterview);
//        
//        String userName = userRepository.findById(updatedInterview.getInterviewer())
//                .map(user -> user.getFullName())
//                .orElse(null);
//        String recruitmentName = recruitmentRepository.findById(updatedInterview.getRecruitment())
//                .map(recruitment -> recruitment.getCandidateName())
//                .orElse(null);
//        
//        InterviewResponse interviewResponse = interviewMapper.toInterviewResponse(updatedInterview);
//        interviewResponse.setInterviewerName(userName);
//        interviewResponse.setRecruitmentName(recruitmentName);
//        
//        return interviewResponse;
//    }

    public void deleteInterview(Integer id) {
        if (!interviewRepository.existsById(id)) {
            throw new WebErrorConfig(ErrorCode.INTERVIEW_NOT_FOUND);
        }
        
        Interview interview = interviewRepository.findById(id).orElse(null);
        if (interview != null) {
            interview.setStatus("deleted");
            interviewRepository.save(interview);
        }
    }

    public InterviewResponse getInterviewById(Integer id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.INTERVIEW_NOT_FOUND));
        
       
        return interviewMapper.toInterviewResponse(interview);
    }
}
