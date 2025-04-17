package com.example.Humosoft.DTO.Request;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
public class RecruitmentRequest {
	private Integer applicationId;
	@NotBlank(message = "Candidate name is required")
	@Size(min = 2, max = 100, message = "Candidate name must be between 2 and 100 characters")
	private String candidateName;

	@NotBlank(message = "Email is required")
	@Email(message = "Email must be valid")
	private String email;


	private String phone;

	@NotBlank(message = "Status is required")
	@Pattern(regexp = "^(PENDING|INTERVIEWING|OFFERED|REJECTED|HIRED)$", message = "Status must be one of: PENDING, INTERVIEWING, OFFERED, REJECTED, HIRED")
	private String status;

	// Trường để nhận file CV (MultipartFile)
	private MultipartFile cvFile;

	@Size(max = 1000, message = "Cover letter must not exceed 1000 characters")
	private String coverLetter;
	

}
