package com.example.Humosoft.Mapper;

import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.PositionRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.Paygrade;
import com.example.Humosoft.Model.Position;
import com.example.Humosoft.Repository.PayGradeRepository;
import com.example.Humosoft.Repository.PositionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PositionMapper {
	private final PayGradeRepository paygradeRepository;
	private final PositionRepository positionRepository;

	public Position toPosition(PositionRequest request) {
	    Paygrade paygrade = paygradeRepository.findByPaygradeName(request.getPaygradeName())
	            .orElseThrow(() -> new WebErrorConfig(ErrorCode.PAYGRADE_NOT_FOUND));

	    // Tạo đối tượng Position và sử dụng setter để gán giá trị
	    Position position = new Position();
	    position.setDescription(request.getDescription());
	    position.setPositionName(request.getPositionName());
	    position.setPaygrade(paygrade);
	    
	    return position;
	}

	public Position updatePosition(Integer id, PositionRequest request) {
		// Tìm kiếm Position hiện tại từ ID
		Position existingPosition = positionRepository.findById(id)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.POSITION_NOT_FOUND));

		// Tìm kiếm paygrade từ tên
		Paygrade paygrade = paygradeRepository.findByPaygradeName(request.getPaygradeName())
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.PAYGRADE_NOT_FOUND));

		// Cập nhật các trường của Position
		existingPosition.setPositionName(request.getPositionName());
		existingPosition.setDescription(request.getDescription());
		existingPosition.setPaygrade(paygrade);

		// Lưu và trả về Position đã được cập nhật
		return positionRepository.save(existingPosition);
	}
}
