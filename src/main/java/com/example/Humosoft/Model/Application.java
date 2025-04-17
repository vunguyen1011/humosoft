package com.example.Humosoft.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Data
@Entity

public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title; // Tiêu đề công việc
	private String description; // Mô tả công việc
	private String requirements; // Yêu cầu ứng viên
	private int numberOfVacancies; // Số lượng tuyển
	private LocalDate startDate; // Ngày bắt đầu đăng tuyển
	private LocalDate endDate; // Ngày kết thúc
	private String status; // Trạng thái: "OPEN", "CLOSED", "DRAFT",...

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department; // Phòng ban tuyển
	@ManyToOne
	@JoinColumn(name = "recruiter_id")
	private Position position; // Vị trí tuyển
	private boolean deleted = false; // Trạng thái xóa

	
}
