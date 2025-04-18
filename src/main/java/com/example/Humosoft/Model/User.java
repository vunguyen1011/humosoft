package com.example.Humosoft.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;
	@Column(name = "quit_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date quitAt;
	private boolean status;
	@Column(name="is_deleted")
	private boolean deleted = false;

	@Embedded
	private Address address;

	// Quan hệ với Role (EAGER để luôn lấy dữ liệu role khi load User)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role;

	// Quan hệ với Position (LAZY để chỉ lấy dữ liệu khi cần)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "position_id")
	private Position position;

	// Quan hệ với Department (LAZY để tối ưu hiệu suất)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private Department department;



	// Quan hệ với Salary (LAZY để chỉ lấy khi cần thiết)

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Salary> salaries;

	private String oldPassword;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date(); // Gán thời gian hiện tại khi entity được tạo
	}
}
