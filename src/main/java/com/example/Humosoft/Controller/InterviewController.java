package com.example.Humosoft.Controller;

import com.example.Humosoft.DTO.Request.InterviewRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.InterviewResponse;
import com.example.Humosoft.DTO.Response.TaskResponse;
import com.example.Humosoft.Model.Interview;
import com.example.Humosoft.Service.InterviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/interviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InterviewController {
    InterviewService interviewService;

    @GetMapping
    public Apiresponse<List<InterviewResponse>> getAllInterviews() {
        return Apiresponse.<List<InterviewResponse>>builder()
                .code(200)
                .message("Success")
                .result(interviewService.getAllInterviews())
                .build();
    }
    @GetMapping("/{id}")
    public Apiresponse<InterviewResponse> getInterviewById(@PathVariable Integer id) {
        return Apiresponse.<InterviewResponse>builder()
                .code(200)
                .message("Success")
                .result(interviewService.getInterviewById(id))
                .build();
    }

    @PostMapping
    public Apiresponse<InterviewResponse> createInterview(@RequestBody InterviewRequest interview) {
    	InterviewResponse interviewResponse=interviewService.createInterview(interview);
        return Apiresponse.<InterviewResponse>builder()
                .code(201)
                .message("Interview created successfully")
                .result(interviewResponse)
                .build();
    }

//    @PutMapping("/{id}")
//    public Apiresponse<InterviewResponse> updateInterview(@PathVariable Integer id, @RequestBody Interview interview) {
//        return Apiresponse.<InterviewResponse>builder()
//                .code(200)
//                .message("Interview updated successfully")
//                .result(interviewService.updateInterview(id, interview))
//                .build();
//    }

    @DeleteMapping("/{id}")
    public Apiresponse<Void> deleteInterview(@PathVariable Integer id) {
        interviewService.deleteInterview(id);
        return Apiresponse.<Void>builder()
                .code(200)
                .message("Interview deleted successfully")
                .result(null)
                .build();
    }
    
}
