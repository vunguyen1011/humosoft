package com.example.Humosoft.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String fullName;
	private String email;
	private String phone;
	private Date dateOfBirth;
	private String gender;
	private String image;
	private String username;
	private String password;
	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;
	private boolean status;
	@Embedded
	private Address address;
	// Quan hệ với Role
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	// Quan hệ với Position
	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;
	// Quan hệ với Department
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	// Quan hệ với Task (ManyToMany)
	@ManyToMany
	@JoinTable(name = "user_tasks", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
	private List<Task> tasks;
	// Mối quan hệ giữa User và Salary
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Salary> salaries;

}
