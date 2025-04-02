package com.example.Humosoft.Controller;

import com.example.Humosoft.DTO.Request.AddDepartmnetRequest;
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
    
    @PatchMapping("/add-departments")
    public Apiresponse<Void> addDepartmentsToTask(@RequestBody AddDepartmnetRequest request) {
		taskService.addDepartmentsToTask(request);
		return Apiresponse.<Void>builder()
				.code(200)
				.message("Add departments to task successfully")
				.build();
    } 
    @GetMapping("/{id}")
    public Apiresponse<TaskResponse> findById(@PathVariable int id) {
    			return Apiresponse.<TaskResponse>builder()
						.code(200)
						.message("Success")
						.result(taskService.findById(id))
						.build();	
    }
    @GetMapping("/department/{departmentId}")
    public Apiresponse<List<TaskResponse>> getTasksByDepartmentId(@PathVariable int departmentId) {
    			return Apiresponse.<List<TaskResponse>>builder()
    					.code(200)
    					.message("Success")
    					.result(taskService.getTasksByDepartmentId(departmentId))
    					.build();
    			
    }
    @PatchMapping("/change/{taskId}")
    public Apiresponse<Void> setChange(@PathVariable Integer taskId){
    	taskService.changeStatus(taskId);
    	return Apiresponse.<Void>builder()
    			.message("change status change")
    			.build();
    }
    
}
