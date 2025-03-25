package com.example.Humosoft.DTO.Response;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
public class DepartmentResponse {
	  private Integer id;
	  private Integer managerId;
    private String departmentName;
    private String description;
    private String managerName;
}
