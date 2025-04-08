package com.example.Humosoft.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.ApplicationRequest;
import com.example.Humosoft.DTO.Response.ApplicationResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.ApplicationMapper;
import com.example.Humosoft.Repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {
	private final ApplicationRepository applicationRepository;
	private final ApplicationMapper applicationMapper;
	public ApplicationResponse create(ApplicationRequest request) {
		var application = applicationMapper.toEntity(request);
		applicationRepository.save(application);
		return applicationMapper.toResponse(application);
	}
	public ApplicationResponse getById(int id) {
		var application = applicationRepository.findById(id).orElseThrow();
		return applicationMapper.toResponse(application);
	}
	public ApplicationResponse update(int id, ApplicationRequest request) {
		var application = applicationRepository.findById(id).orElseThrow();
		application.setTitle(request.getTitle());
		application.setDescription(request.getDescription());
		application.setNumberOfVacancies(request.getNumberOfVacancies());
		application.setStartDate(request.getStartDate());
		application.setEndDate(request.getEndDate());
		applicationRepository.save(application);
		return applicationMapper.toResponse(application);
	}
	public List<ApplicationResponse> getAll() {
		var applications = applicationRepository.findAll();
		return applications.stream()
				.filter(application -> application.getStatus().equals("OPEN"))
				.filter(application -> application.isDeleted() == false)	
				.map(applicationMapper::toResponse).toList();
	}
	public List<ApplicationResponse> getAllforAdmin() {
		var applications = applicationRepository.findAll();
		return applications.stream()				
				.map(applicationMapper::toResponse).toList();
	}
	public void delete(int id) {
		var application = applicationRepository.findById(id).orElseThrow();
		application.setDeleted(true);
		applicationRepository.save(application);
	}
	public  void closeApplication(int id) {
		var application = applicationRepository.findById(id).orElseThrow(()->new WebErrorConfig(ErrorCode.APPLICATION_NOT_FOUND));
		application.setStatus("CLOSED");
		applicationRepository.save(application);
	}
	
	
}
