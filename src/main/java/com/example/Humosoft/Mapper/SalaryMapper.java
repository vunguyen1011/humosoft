package com.example.Humosoft.Mapper;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Response.SalaryResponse;
import com.example.Humosoft.Model.Salary;

@Component

public class SalaryMapper {

	 public SalaryResponse toSalaryResponse(Salary salary) {
		return SalaryResponse.builder()
				.id(salary.getId())
				.userId(salary.getUser().getId())
				.fullname(salary.getUser().getFullName())
				.image(salary.getUser().getImage())
				.month(salary.getMonth())
				.year(salary.getYear())
				.basicSalary(salary.getBasicSalary())
				.bonuses(salary.getBonuses())
				.deductions(salary.getDeductions())
				.netSalary(salary.getNetSalary())
				.allowences(salary.getAllowences())
				.grossSalary(salary.getGrossSalary())		
				.commission(salary.getCommission())
				.build();
			
	}
}
