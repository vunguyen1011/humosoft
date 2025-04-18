package com.example.Humosoft.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.ApplicationRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.ApplicationResponse;
import com.example.Humosoft.Service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/applications")	
@RequiredArgsConstructor
public class ApplicationController {
	private final ApplicationService applicationService;
	 @PostMapping
	 public Apiresponse<ApplicationResponse> create(@RequestBody ApplicationRequest request) {
		 var application = applicationService.create(request);
		 return Apiresponse.<ApplicationResponse>builder().code(200).message("Create application successfully")
				 .result(application).build();
		 
	 }
	 @GetMapping("/getById/{id}")
	 public Apiresponse<ApplicationResponse> getById(@PathVariable Integer id) {
		 var application = applicationService.getById(id);
		 return Apiresponse.<ApplicationResponse>builder().code(200).message("Get application successfully")
				 .result(application).build();
	 }
	 @PostMapping("/update/{id}")
	 	public Apiresponse<ApplicationResponse> update(@PathVariable Integer id,@RequestBody ApplicationRequest request) {
		 var application = applicationService.update(id, request);
		 return Apiresponse.<ApplicationResponse>builder().code(200).message("Update application successfully")
				 .result(application).build();
	 }
	 @GetMapping("/getAll")
	 public Apiresponse<List<ApplicationResponse>> getAll() {
		 var applications = applicationService.getAll();
		 return Apiresponse.<List<ApplicationResponse>>builder().code(200).message("Get all applications successfully")
				 .result(applications).build();
	 }
	 @PatchMapping("/delete/{id}")
	 public Apiresponse<Void> delete(@PathVariable Integer id) {
		 applicationService.delete(id);
		 return Apiresponse.<Void>builder().code(200).message("Delete application successfully").build();
	 }
	 @PatchMapping("/closeApplication/{id}")
	 public Apiresponse<Void> closeApplication(@PathVariable int id) {
		 applicationService.closeApplication(id);
		 return Apiresponse.<Void>builder().code(200).message("Close application successfully").build();
}
	 @GetMapping("/getAllforAdmin")
	 public Apiresponse<List<ApplicationResponse>> getAllforAdmin() {
		 var applications = applicationService.getAllforAdmin();
		 return Apiresponse.<List<ApplicationResponse>>builder().code(200).message("Get all applications successfully")
				 .result(applications).build();
	 }
	 
}
