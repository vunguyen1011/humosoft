package com.example.Humosoft.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class SubTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String subTaskName;
	
	private LocalDate startDate;
	private LocalDate endDate;
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;
	@ElementCollection
	private List<Integer> userIds=new ArrayList<>();
	private String status;
}
