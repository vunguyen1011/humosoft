package com.example.Humosoft.Mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.RecruitmentRequest;
import com.example.Humosoft.Model.Recruitment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecruitmentMapper {

public Recruitment toRecruitment(RecruitmentRequest recruitment) {

	return Recruitment.builder()
			.candidateName(recruitment.getCandidateName())
			.applicationId(recruitment.getApplicationId())
			.email(recruitment.getEmail())
			.phone(recruitment.getPhone())
			.status(recruitment.getStatus())
			.cvPath(recruitment.getCvFile().getOriginalFilename()) // Lưu tên file CV
			.coverLetter(recruitment.getCoverLetter())
			.applicationDate(LocalDate.now()) // Ngày nộp đơn là ngày hiện tại
			.build();
}
}
