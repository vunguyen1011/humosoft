package com.example.Humosoft.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Humosoft.DTO.Request.PayGradeRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.Model.Paygrade;

import com.example.Humosoft.Service.PaygradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paygrades")
public class PaygradeController {

	private final PaygradeService paygradeService;

	// Endpoint to create a new Paygrade
	@PostMapping
	public Apiresponse<Paygrade> createPaygrade(@RequestBody PayGradeRequest paygradeRequest) {
		Paygrade paygrade = paygradeService.create(paygradeRequest);
		return Apiresponse.<Paygrade>builder().code(201).message("Paygrade created successfully").result(paygrade)
				.build();
	}

	// Endpoint to update an existing Paygrade
	@PutMapping("/{id}")
	public Apiresponse<Paygrade> updatePaygrade(@PathVariable Integer id,
			@RequestBody PayGradeRequest paygradeRequest) {
		Paygrade updatedPaygrade = paygradeService.update(id, paygradeRequest);

		return Apiresponse.<Paygrade>builder().code(200).message("Paygrade updated successfully")
				.result(updatedPaygrade).build();

	}

	// Endpoint to get a specific Paygrade by id
	@GetMapping("/{id}")
	public Apiresponse<Paygrade> getPaygradeById(@PathVariable Integer id) {
		Paygrade paygrade = paygradeService.getById(id);

		return Apiresponse.<Paygrade>builder().code(200).message("Paygrade found").result(paygrade).build();

	}

	// Endpoint to delete a Paygrade by id
	@DeleteMapping("/{id}")
	public Apiresponse<Void> deletePaygrade(@PathVariable Integer id) {
		paygradeService.delete(id);

		return Apiresponse.<Void>builder().code(204).message("Paygrade deleted successfully").result(null).build();

	}
	@GetMapping
	public Apiresponse<List<Paygrade>> getAllPaygrades() {
		List<Paygrade> paygrades = paygradeService.getAll();
		return Apiresponse.<List<Paygrade>>builder().code(200).message("Success").result(paygrades).build();
	}
}
