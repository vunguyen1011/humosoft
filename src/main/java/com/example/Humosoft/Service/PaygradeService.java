package com.example.Humosoft.Service;

import org.springframework.stereotype.Service;
import com.example.Humosoft.DTO.Request.PayGradeRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.PaygradeMapper;
import com.example.Humosoft.Model.Paygrade;
import com.example.Humosoft.Repository.PayGradeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaygradeService {

	private final PayGradeRepository paygradeRepository;
	private final PaygradeMapper paygradeMapper;

	// Create a new PayGrade
	public Paygrade create(PayGradeRequest paygradeRequest) {
		Paygrade paygrade = paygradeMapper.toPaygrade(paygradeRequest); // Chuyển đổi từ PayGradeRequest sang PayGrade
		if(paygradeRepository.existsByPaygradeName(paygradeRequest.getPaygradeName())) {
			throw new WebErrorConfig(ErrorCode.PAYGRADE_ALREADY_EXISTS);
		}
		return paygradeRepository.save(paygrade); // Lưu vào cơ sở dữ liệu và trả về đối tượng PayGrade đã lưu
	}

	// Get all PayGrades
	public List<Paygrade> getAll() {
		return paygradeRepository.findByDeletedFalse();
	}

	// Get PayGrade by ID
	public Paygrade getById(Integer id) {
		return paygradeRepository.findById(id).orElseThrow(()->new WebErrorConfig(ErrorCode.PAYGRADE_NOT_FOUND));
	}

	// Update PayGrade by ID
	public Paygrade update(Integer id, PayGradeRequest paygradeRequest) {
		Optional<Paygrade> existingPaygrade = paygradeRepository.findById(id); // Kiểm tra xem PayGrade có tồn tại không
		if (existingPaygrade.isPresent()) {
			Paygrade paygrade = existingPaygrade.get();
			paygradeMapper.updatePaygrade(paygrade, paygradeRequest); // Cập nhật PayGrade
			return paygradeRepository.save(paygrade); // Lưu vào cơ sở dữ liệu và trả về đối tượng PayGrade đã cập nhật
		} else {
			return null; // Nếu không tìm thấy PayGrade với ID đó, trả về null
		}
	}

	// Delete PayGrade by ID
	public void delete(Integer id) {
	    Paygrade paygrade = paygradeRepository.findById(id)
	            .orElseThrow(() -> new WebErrorConfig(ErrorCode.PAYGRADE_NOT_FOUND));
	    
	    paygrade.setDeleted(true);  // Đánh dấu là đã xóa
	    paygradeRepository.save(paygrade); // Lưu lại thay vì xóa khỏi DB
	}
	public List<Paygrade> findAll() {
		return paygradeRepository.findAll();
	}
}
