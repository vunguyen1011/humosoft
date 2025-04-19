package com.example.Humosoft.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.RecruitmentRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.Model.Recruitment;
import com.example.Humosoft.Service.RecruitmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recruitments")
@RequiredArgsConstructor
public class RecruitmentController {
	private final RecruitmentService recruitmentService;

	@PostMapping("/create")
	public Apiresponse<Recruitment> createRecruitment(@Valid @ModelAttribute RecruitmentRequest recruitmentRequest) {
		var recruitment = recruitmentService.saveRecruitment(recruitmentRequest);
		return Apiresponse.<Recruitment>builder().message("Create recruitment successfully").result(recruitment)
				.build();
	}

	@GetMapping("/applicationName")
	public Apiresponse<List<Recruitment>> getAllByApplication(@RequestParam String applicationName) {
		List<Recruitment> recruitments=recruitmentService.getByApplication(applicationName);
		return Apiresponse.<List<Recruitment>>builder()
				.result(recruitments)
				.build();
	}
	@GetMapping("/change/{id}")
	public Apiresponse<Recruitment> changeStatus(@PathVariable  Integer id,@RequestParam String status ){
		Recruitment recruitment=recruitmentService.setChange(id, status);
		System.out.print("ABC");
		return Apiresponse.<Recruitment>builder()
				.result(recruitment)
				.build();
	}

}
