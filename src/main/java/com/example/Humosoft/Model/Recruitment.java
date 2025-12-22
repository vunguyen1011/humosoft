package com.example.Humosoft.Model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "recruitment")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Recruitment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String candidateName;
	private String email;
	private String phone;
	private String status="PENDING";
	private String cvPath; // Thêm trường để lưu đường dẫn file CV
	private String coverLetter;
	private LocalDate applicationDate;
	@Column(name ="application_name")
	private String  applicationName;

}
