package com.example.Humosoft.DTO.Request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentAddEmployeesRequest {
    private Integer departmentId;
    private List<Integer> userIds;
}
