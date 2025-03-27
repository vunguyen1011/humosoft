package com.example.Humosoft.DTO.Request;

import java.util.List;

import lombok.Data;
@Data
public class AddUserToSubTaskRequest {
	private Integer subTaskId;
    private List<Integer> userIds;
}
