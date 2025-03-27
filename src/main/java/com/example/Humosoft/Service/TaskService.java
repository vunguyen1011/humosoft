package com.example.Humosoft.Service;

import com.example.Humosoft.DTO.Request.AddDepartmnetRequest;
import com.example.Humosoft.DTO.Request.SubTaskRequest;
import com.example.Humosoft.DTO.Request.TaskRequest;
import com.example.Humosoft.DTO.Response.SubTaskResponse;
import com.example.Humosoft.DTO.Response.TaskResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.SubTaskMapper;
import com.example.Humosoft.Mapper.TaskMapper;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.SubTask;
import com.example.Humosoft.Model.Task;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.DepartmentRepository;
import com.example.Humosoft.Repository.SubTaskRepository;
import com.example.Humosoft.Repository.TaskRepository;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional	
public class TaskService {
    TaskRepository repository;
    TaskMapper mapper;
    DepartmentRepository departmentRepository;	
    UserService userService;
    SubTaskRepository subTaskRepository;
   SubTaskMapper subTaskMapper;
    // tao 1 task moi
    public TaskResponse createTask(TaskRequest request) {
        Task task = mapper.toTask(request);
        if(repository.existsTaskByTaskName(request.getTaskName()))
            throw new WebErrorConfig(ErrorCode.TASK_ALREADY_EXISTS);
        Task savedTask =repository.save(task);
        return mapper.toTaskResponse(savedTask);
    }

    // cap nhat task
    public TaskResponse updateTask(int taskId ,TaskRequest request) {
        Task task = repository.findById(taskId).orElseThrow(() ->
                new WebErrorConfig(ErrorCode.TASK_NOT_FOUND));
        mapper.updateTask(task, request);
        repository.save(task);
        return mapper.toTaskResponse(task);
    }

    //get all tasks
    public List<TaskResponse> getAllTasks() {
        List<Task> task = repository.findAll();
        return task.stream().map(mapper::toTaskResponse).toList();
    }

    //delete task by id
    public void deleteTask(int taskId) {
        Task task = repository.findById(taskId).orElseThrow(() ->
                new WebErrorConfig(ErrorCode.TASK_NOT_FOUND));
        task.setEnabled(false);
        repository.save(task);
    }
    
    public TaskResponse findById(int taskId) {
		Task task = repository.findById(taskId).orElseThrow(() ->
				new WebErrorConfig(ErrorCode.TASK_NOT_FOUND));
		return mapper.toTaskResponse(task);
	}
    
    public void addDepartmentsToTask(AddDepartmnetRequest request) {
        Task task = repository.findById(request.getTaskId()).orElseThrow(() ->
                new WebErrorConfig(ErrorCode.TASK_NOT_FOUND));
        for (Integer departmentId :request.getDepartmentIds()) {	
            Department department = departmentRepository.findById(departmentId).orElseThrow(() ->
                    new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
            task.getDepartments().add(department);
        }
        Task updatedTask = repository.save(task);
}
    public List<TaskResponse> getTasksByDepartmentId(Integer departmentId) {
    	Department department = departmentRepository.findById(departmentId).orElseThrow(() ->new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
        List<Task> tasks = repository.findByDepartments_Id(departmentId);
        return tasks.stream().map(mapper::toTaskResponse).toList();
    }
}
   

