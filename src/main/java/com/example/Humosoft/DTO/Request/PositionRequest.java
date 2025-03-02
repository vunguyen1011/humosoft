package com.example.Humosoft.DTO.Request;

import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.Paygrade;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PositionRequest {
	private String positionName;

	private String paygradeName;
	private String description;
}
