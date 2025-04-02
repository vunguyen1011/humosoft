package com.example.Humosoft.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.SubTaskRequest;
import com.example.Humosoft.DTO.Response.SubTaskResponse;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.SubTaskMapper;
import com.example.Humosoft.Model.SubTask;
import com.example.Humosoft.Model.Task;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.SubTaskRepository;
import com.example.Humosoft.Repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubTaskService {
	private final SubTaskRepository subTaskRepository;
	private final SubTaskMapper subTaskMapper;
	private final UserService userService;
	private final TaskRepository taskRepository;

	public SubTaskResponse createSubTask(SubTaskRequest request) {
		
		Task task = taskRepository.findById(request.getTaskId())
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.TASK_NOT_FOUND));
		SubTask subTask = subTaskMapper.toSubTask(request);
		subTask.setStatus("TO-DO");
		subTask.setTask(task);
		SubTask savedSubTask = subTaskRepository.save(subTask);
		return subTaskMapper.toSubTaskResponse(savedSubTask);
	}

	public List<SubTaskResponse> getAllSubTasks() {
		List<SubTask> subTasks = subTaskRepository.findAll();
		return subTasks.stream().map(subTaskMapper::toSubTaskResponse).toList();
	}

	public List<SubTaskResponse> getSubTasksByUserId(Integer userId) {
		userService.findUerById(userId);
		List<SubTask> subTasks = subTaskRepository.findByUserIdsContaining(userId);
		return subTasks.stream().map(subTaskMapper::toSubTaskResponse).toList();
	}

	public List<SubTaskResponse> getSubTasksByTaskId(Integer taskId) {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new WebErrorConfig(ErrorCode.TASK_NOT_FOUND));
		List<SubTask> subTasks = subTaskRepository.findByTaskId(taskId);
		return subTasks.stream().map(subTaskMapper::toSubTaskResponse).toList();
	}

	public void AddUsersToSubTask(Integer subTaskId, List<Integer> userIds) {
		SubTask subTask = subTaskRepository.findById(subTaskId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.SUBTASK_NOT_FOUND));
		for (Integer userId : userIds) {
			User user = userService.findUerById(userId);
			subTask.getUserIds().add(user.getId());
		}
		subTaskRepository.save(subTask);
	}

	public void deleteSubTask(Integer subTaskId) {
		SubTask subTask = subTaskRepository.findById(subTaskId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.SUBTASK_NOT_FOUND));
		subTaskRepository.delete(subTask);
	}

	public void deleteUsersFromSubTask(Integer subTaskId, List<Integer> userIds) {
		SubTask subTask = subTaskRepository.findById(subTaskId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.SUBTASK_NOT_FOUND));
		for (Integer userId : userIds) {
			User user = userService.findUerById(userId);
			subTask.getUserIds().remove(user.getId());
		}
		subTaskRepository.save(subTask);
	}

	public List<UserResponse> getAllUserBySubTaskId(Integer subTaskId) {
		SubTask subTask = subTaskRepository.findById(subTaskId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.SUBTASK_NOT_FOUND));
		List<User> users = subTask.getUserIds().stream().map(userService::findUerById).toList();
		return users.stream().map(userService.getUserMapper()::toUserResponse).toList();
	}

	public void changeStatus(Integer subTaskId) {
	    SubTask subtask = subTaskRepository.findById(subTaskId)
	            .orElseThrow(() -> new WebErrorConfig(ErrorCode.SUBTASK_NOT_FOUND));
	    if (LocalDate.now().isAfter(subtask.getEndDate())) {
	        subtask.setStatus("Overdue");
	    } else {
	        subtask.setStatus("Complete");
	    }
	    subTaskRepository.save(subtask);
	}
	public SubTaskResponse findById(Integer id) {
		SubTask subTask=subTaskRepository.findById(id).orElseThrow(()->new WebErrorConfig(ErrorCode.SUBTASK_NOT_FOUND));
		return subTaskMapper.toSubTaskResponse(subTask);
	}
}
