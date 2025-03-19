package com.example.Humosoft.Controller;

import com.example.Humosoft.DTO.Request.TaskRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.TaskResponse;
import com.example.Humosoft.Service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/tasks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskService taskService;

    @GetMapping
    public Apiresponse<List<TaskResponse>> getAllTasks() {
        return Apiresponse.<List<TaskResponse>>builder()
                .code(200)
                .message("Success")
                .result(taskService.getAllTasks())
                .build();
    }

    @PatchMapping("/{id}")
    public Apiresponse<TaskResponse> updateTask(@PathVariable int id ,@RequestBody TaskRequest taskRequest) {
        return Apiresponse.<TaskResponse>builder()
                .code(200)
                .message("Success")
                .result(taskService.updateTask(id, taskRequest))
                .build();
    }

    @PostMapping
    public Apiresponse<TaskResponse> addTask(@RequestBody TaskRequest taskRequest) {
        return Apiresponse.<TaskResponse>builder()
                .code(200)
                .message("Success")
                .result(taskService.createTask(taskRequest))
                .build();
    }

    @PatchMapping("/delete/{id}")
    public Apiresponse<Void> deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return Apiresponse.<Void>builder()
                .code(200)
                .message("Delete task successfully")
                .build();
    }
}
