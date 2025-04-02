package com.example.Humosoft.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.AddUserToSubTaskRequest;
import com.example.Humosoft.DTO.Request.SubTaskRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.SubTaskResponse;
import com.example.Humosoft.Service.SubTaskService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subtasks")
public class SubTaskController {
	private final SubTaskService subTaskService;
	
	@PostMapping
public Apiresponse<SubTaskResponse> createSubTask(@RequestBody SubTaskRequest subTaskRequest) {
		SubTaskResponse subTaskResponse = subTaskService.createSubTask(subTaskRequest);
		return Apiresponse.<SubTaskResponse>builder()
				.result(subTaskResponse)
				.build();	
	}
	@GetMapping("/user/{userId}")
	public Apiresponse<List<SubTaskResponse>> getSubTasksByUserId(@PathVariable Integer userId) {
		List<SubTaskResponse> subTaskResponses = subTaskService.getSubTasksByUserId(userId);
		return Apiresponse.<List<SubTaskResponse>>builder()
				.result(subTaskResponses)
				.build();
	}
	@GetMapping("/task/{taskId}")	
	public Apiresponse<List<SubTaskResponse>> getSubTasksByTaskId(@PathVariable Integer taskId) {
		List<SubTaskResponse> subTaskResponses = subTaskService.getSubTasksByTaskId(taskId);
		return Apiresponse.<List<SubTaskResponse>>builder()
				.result(subTaskResponses)
				.build();
	}
	
	@PatchMapping("/addUsers")
	public Apiresponse<Void> AddUsersToSubTask(@RequestBody  AddUserToSubTaskRequest addUserToSubTaskRequest) {
		subTaskService.AddUsersToSubTask(addUserToSubTaskRequest.getSubTaskId(), addUserToSubTaskRequest.getUserIds());
		return Apiresponse.<Void>builder()
				.message("Users added to subtask successfully")
				.build();
	}
	@PatchMapping("/delete/{subTaskId}")
	public Apiresponse<Void> deleteSubTask(@PathParam("subTaskId") Integer subTaskId) {
		subTaskService.deleteSubTask(subTaskId);
		return Apiresponse.<Void>builder()
				.message("Subtask deleted successfully")
				.build();
	}
	@GetMapping
	public Apiresponse<List<SubTaskResponse>> getAllSubTasks() {
		List<SubTaskResponse> subTaskResponses = subTaskService.getAllSubTasks();
		return Apiresponse.<List<SubTaskResponse>>builder()
				.result(subTaskResponses)
				.build();
	}
	@PatchMapping("/change/{subTaskId}")
    public Apiresponse<Void> setChange(@PathVariable Integer subTaskId){
    	subTaskService.changeStatus(subTaskId);
    	return Apiresponse.<Void>builder()
    			.message("change status change")
    			.build();
    }
	@GetMapping("/{id}")
	public Apiresponse<SubTaskResponse> findById(@PathVariable Integer id){
		return Apiresponse.<SubTaskResponse>builder()
				.result(subTaskService.findById(id))
				.build();
	}
	
	
	
	
}
