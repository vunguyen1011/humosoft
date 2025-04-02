package com.example.Humosoft.Mapper;

import com.example.Humosoft.DTO.Request.TaskRequest;
import com.example.Humosoft.DTO.Response.TaskResponse;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.Task;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
@Component

public class TaskMapper {
	 public  Task toTask(TaskRequest request) {
	        if (request == null) {
	            return null;
	        }

	        Task task = new Task();
	        
	        task.setTaskName(request.getTaskName());
	        task.setDescription(request.getDescription());
	        task.setStartDate(request.getStartDate());
	        task.setEndDate(request.getEndDate());
	        task.setStatus(request.getStatus());
	        task.setPriority(request.getPriority());
	        task.setEnabled(request.isEnabled());

	        return task;
	    }
    public  TaskResponse toTaskResponse(Task task) {
        if (task == null) {
            return null;
        }
        
        List<String> departmentNames = task.getDepartments().stream()
				.map(Department::getDepartmentName)
				.collect(Collectors.toList());

        return TaskResponse.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .departmentName(departmentNames) // Gán List<String> vào đây
                .enabled(task.isEnabled())
                .build();
    }
    public  void updateTask(Task task, TaskRequest request) {
        if (task == null || request == null) {
            return;
        }

        if (request.getTaskName() != null) {
            task.setTaskName(request.getTaskName());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStartDate() != null) {
            task.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            task.setEndDate(request.getEndDate());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        task.setEnabled(request.isEnabled()); // Cờ boolean có thể giữ nguyên nếu request không có giá trị
    }
}
