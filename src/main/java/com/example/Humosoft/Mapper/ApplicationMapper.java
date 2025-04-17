package com.example.Humosoft.Mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.ApplicationRequest;
import com.example.Humosoft.DTO.Response.ApplicationResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.Application;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.Position;
import com.example.Humosoft.Repository.ApplicationRepository;
import com.example.Humosoft.Repository.DepartmentRepository;
import com.example.Humosoft.Repository.PositionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {
	private final DepartmentRepository departmentRepository;
	private final PositionRepository positionRepository;
	private final ApplicationRepository applicationRepository;

	public Application toEntity(ApplicationRequest request) {
		if (request == null) {
			return null;
		}

		Application application = new Application();
		application.setTitle(request.getTitle());
		application.setDescription(request.getDescription());

		application.setNumberOfVacancies(request.getNumberOfVacancies());
		application.setStartDate(request.getStartDate());
		application.setEndDate(request.getEndDate());
		application.setStatus("OPEN");

		// Chuyển đổi Department
		if (request.getDepartmentId() != null) {
			Department department = departmentRepository.findById(request.getDepartmentId())
					.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

			application.setDepartment(department);
		}
		// Chuyển đổi Position
		if (request.getPositionId() != null) {
			Position position = positionRepository.findById(request.getPositionId())
					.orElseThrow(() -> new WebErrorConfig(ErrorCode.POSITION_NOT_FOUND));
			application.setPosition(position);
		}

		return application;
	}
	public ApplicationResponse toResponse(Application application) {
		if (application == null) {
			return null;
		}
		return ApplicationResponse.builder().id(application.getId()).title(application.getTitle())
				.description(application.getDescription()).numberOfVacancies(application.getNumberOfVacancies())
				.startDate(application.getStartDate()).endDate(application.getEndDate()).status(application.getStatus())
				.departmentName(application.getDepartment().getDepartmentName())
				.positionName(application.getPosition().getPositionName()).build();

	}
}
