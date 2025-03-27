package com.example.Humosoft.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Humosoft.Model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsTaskByTaskName(String taskName);
    List<Task> findByDepartments_Id(Integer departmentId);
}
