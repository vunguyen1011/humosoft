package com.example.Humosoft.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.StructuredTaskScope.Subtask;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Humosoft.Model.SubTask;

public interface SubTaskRepository  extends JpaRepository<SubTask, Integer> {
	boolean existsBySubTaskName(String subTaskName);
	List<SubTask> findByTaskId(Integer taskId);
	Optional<SubTask> findBySubTaskName(String subTaskName);
	Optional<SubTask> findById(Integer subTaskId);
	List<SubTask> findByUserIdsContaining(Integer userId);
	
}


