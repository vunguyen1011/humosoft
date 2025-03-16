package com.example.Humosoft.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.Humosoft.DTO.Request.TimeOffRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.TimeOffResponse;
import com.example.Humosoft.Service.TimeOffService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timeoff")
public class TimeOffController {
    
    private final TimeOffService timeOffService;

    // Tạo đơn nghỉ phép
    @PostMapping
    public Apiresponse<String> submitTimeOffRequest(@RequestBody TimeOffRequest request) {
        timeOffService.submitTimeOffRequest(request);
        return Apiresponse.<String>builder()
                .result("Đơn nghỉ phép đã được gửi!")
                .build();
    }

    // Lấy tất cả đơn nghỉ phép
    @GetMapping
    public Apiresponse<List<TimeOffResponse>> getAllTimeOffRequests() {
        List<TimeOffResponse> timeOffResponses = timeOffService.getAllTimeOffRequests();
        return Apiresponse.<List<TimeOffResponse>>builder()
                .result(timeOffResponses)
                .build();
    }

    // Lấy đơn nghỉ phép theo phòng ban
    @GetMapping("/by-department")
    public Apiresponse<List<TimeOffResponse>> getTimeOffByDepartment(@RequestParam String departmentName) {
        List<TimeOffResponse> timeOffResponses = timeOffService.getTimeOffRequestsByDepartment(departmentName);
        return Apiresponse.<List<TimeOffResponse>>builder()
                .result(timeOffResponses)
                .build();
    }
    // ✅ Phê duyệt đơn nghỉ phép
    @PutMapping("/{timeOffId}/approve")
    public Apiresponse<String> approveTimeOff(@PathVariable Integer timeOffId) {
        timeOffService.approveTimeOff(timeOffId);
        return Apiresponse.<String>builder()
                .result("Đơn nghỉ phép đã được phê duyệt.")
                .build();
    }

    // ❌ Từ chối đơn nghỉ phép
    @PutMapping("/{timeOffId}/reject")
    public Apiresponse<String> rejectTimeOff(@PathVariable Integer timeOffId) {
        timeOffService.rejectTimeOff(timeOffId);
        return Apiresponse.<String>builder()
                .result("Đơn nghỉ phép đã bị từ chối.")
                .build();
    }
    @GetMapping("/by-department-excluding-manager")
    public Apiresponse<List<TimeOffResponse>> getTimeOffByDepartmentExcludingManager(@RequestParam String departmentName) {
        List<TimeOffResponse> timeOffResponses = timeOffService.getTimeOffRequestsByDepartmentExcludingManager(departmentName);
        return Apiresponse.<List<TimeOffResponse>>builder()
                .result(timeOffResponses)
                .build();
    }


}
