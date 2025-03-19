package com.example.Humosoft.Service;

import com.example.Humosoft.DTO.Request.TaskRequest;
import com.example.Humosoft.DTO.Response.TaskResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.TaskMapper;
import com.example.Humosoft.Model.Task;
import com.example.Humosoft.Repository.TaskRepository;
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
public class TaskService {
    TaskRepository repository;
    TaskMapper mapper;
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

}
