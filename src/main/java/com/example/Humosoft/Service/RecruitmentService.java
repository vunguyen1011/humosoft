package com.example.Humosoft.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.RecruitmentRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.RecruitmentMapper;
import com.example.Humosoft.Model.Recruitment;
import com.example.Humosoft.Repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentService {
	private final RecruitmentRepository recruitmentRepository;
	private final RecruitmentMapper recruitmentMapper;
	private final FileService fileService;
	/**
	 * Lưu thông tin tuyển dụng
	 * @param recruitmentRequest Thông tin tuyển dụng
	 * @return Đối tượng Recruitment đã được lưu vào cơ sở dữ liệu
	 */
	public Recruitment saveRecruitment(RecruitmentRequest recruitmentRequest) {
		if(recruitmentRepository.existsById(recruitmentRequest.getApplicationId())) {
			throw new WebErrorConfig(ErrorCode.APPLICATION_NOT_FOUND);
		}
		var recruitment = recruitmentMapper.toRecruitment(recruitmentRequest);
		recruitmentRepository.save(recruitment);
		fileService.saveFile(recruitmentRequest.getCvFile());
		
		return recruitment;
	}
	public List<Recruitment> getByApplication(String applicationName){
		return recruitmentRepository.findByApplicationName(applicationName);
	}
	public Recruitment setChange(Integer id,String status) {
			Recruitment recruitment=recruitmentRepository.findById(id).orElseThrow(()->new WebErrorConfig(ErrorCode.RECRUITMENT_NOT_FOUND));
			recruitment.setStatus(status);
			recruitmentRepository.save(recruitment);
			return recruitment;
	}
}
