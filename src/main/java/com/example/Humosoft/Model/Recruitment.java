package com.example.Humosoft.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Recruitment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String candidateName;
	private String email;
	private String phone;

	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	private String status;

}
