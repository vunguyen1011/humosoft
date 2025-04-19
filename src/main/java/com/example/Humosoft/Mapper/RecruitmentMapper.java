package com.example.Humosoft.Mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.RecruitmentRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.Application;
import com.example.Humosoft.Model.Recruitment;
import com.example.Humosoft.Repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecruitmentMapper {
	private final  ApplicationRepository applicationRepository;
	
	public Recruitment toRecruitment(RecruitmentRequest recruitment) {
		Application application=applicationRepository.findById(recruitment.getApplicationId()).orElseThrow(()->new WebErrorConfig(ErrorCode.APPLICATION_NOT_FOUND)); 
		return Recruitment.builder()
				.candidateName(recruitment.getCandidateName())
				.applicationName(application.getTitle())
				.email(recruitment.getEmail())
				.phone(recruitment.getPhone())
				.status(recruitment.getStatus())
				.cvPath(recruitment.getCvFile().getOriginalFilename()) // Lưu tên file CV
				.coverLetter(recruitment.getCoverLetter())
				.applicationDate(LocalDate.now()) // Ngày nộp đơn là ngày hiện tại
				.build();
	}	

}
