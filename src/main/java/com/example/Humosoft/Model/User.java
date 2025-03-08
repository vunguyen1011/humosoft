package com.example.Humosoft.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

	private boolean gender;
	private String image;
	private String username;
	private String password;

	@Column(name = "created_at", columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;
	@Column(name = "quit_at", columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
	private Date quitAt;
	private boolean status;
	private boolean deleted=false;
	@Embedded
	private Address address;
	// Quan hệ với Role
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role;
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
	private String oldPassword;
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date(); // Gán thời gian hiện tại khi entity được tạo
	}

}
