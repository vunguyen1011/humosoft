package com.example.Humosoft.DTO.Request;

import java.util.List;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class AddDepartmnetRequest {
	Integer taskId;
	List<Integer> departmentIds;
}
