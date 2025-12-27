package com.example.Humosoft.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DepartmentRequest {
    @NotBlank(message = "Department name không được để trống")
    @Size(min = 2, max = 100, message = "Department name phải từ 2 đến 100 ký tự")
    private String departmentName;
    private String description;
}
