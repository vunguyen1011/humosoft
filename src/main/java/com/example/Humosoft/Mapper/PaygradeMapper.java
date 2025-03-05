package com.example.Humosoft.Mapper;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.PayGradeRequest;
import com.example.Humosoft.Model.Paygrade;

import lombok.Builder;

@Builder
@Component
public class PaygradeMapper {
public Paygrade toPaygrade(PayGradeRequest paygradeRequest) {
	Paygrade paygrade= new Paygrade();
	paygrade.setBaseSalary(paygradeRequest.getBaseSalary());
	paygrade.setPaygradeName(paygradeRequest.getPaygradeName());
	paygrade.setDescription(paygradeRequest.getDescription());
	return paygrade;
}
public Paygrade updatePaygrade(Paygrade existingPaygrade, PayGradeRequest paygradeRequest) {
    if (paygradeRequest.getBaseSalary() != null) {
        existingPaygrade.setBaseSalary(paygradeRequest.getBaseSalary());
    }
    if (paygradeRequest.getPaygradeName() != null) {
        existingPaygrade.setPaygradeName(paygradeRequest.getPaygradeName());
    }
    if (paygradeRequest.getDescription() != null) {
        existingPaygrade.setDescription(paygradeRequest.getDescription());
    }
    return existingPaygrade;
}
}
