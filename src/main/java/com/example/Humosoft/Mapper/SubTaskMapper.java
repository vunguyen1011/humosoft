package com.example.Humosoft.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.SubTaskRequest;
import com.example.Humosoft.DTO.Response.SubTaskResponse;
import com.example.Humosoft.DTO.Response.TaskResponse;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Model.SubTask;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.TaskRepository;
import com.example.Humosoft.Service.TaskService;
import com.example.Humosoft.Service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubTaskMapper {
	private final  UserService userService;
	private final TaskRepository taskRepository;
    public SubTaskResponse toSubTaskResponse(SubTask subTask) {
    	List<String> fullNames=subTask.getUserIds().stream().map(userId -> {
			User user = userService.findUerById(userId);
			return user.getFullName();
		}).toList();
		return SubTaskResponse.builder()
				.id(subTask.getId())
				.subTaskName(subTask.getSubTaskName())
				.fullName(fullNames)
				.startDate(subTask.getStartDate())
				.endDate(subTask.getEndDate())
				.taskName(subTask.getTask().getTaskName())
				.status(subTask.getStatus())
				.build();
	}
	public SubTask toSubTask(SubTaskRequest request) {
		SubTask subTask = new SubTask();
		subTask.setSubTaskName(request.getSubTaskName());
		subTask.setStartDate(request.getStartDate());
		subTask.setEndDate(request.getEndDate());
		return subTask;
	}

}
