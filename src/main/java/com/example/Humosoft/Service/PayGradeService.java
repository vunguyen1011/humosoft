package com.example.Humosoft.Service;

import org.springframework.stereotype.Service;
import com.example.Humosoft.DTO.Request.PayGradeRequest;
import com.example.Humosoft.Mapper.PaygradeMapper;
import com.example.Humosoft.Model.Paygrade;
import com.example.Humosoft.Repository.PayGradeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayGradeService {

	private final PayGradeRepository paygradeRepository;
	private final PaygradeMapper paygradeMapper;

	// Create a new PayGrade
	public Paygrade create(PayGradeRequest paygradeRequest) {
		Paygrade paygrade = paygradeMapper.toPaygrade(paygradeRequest); // Chuyển đổi từ PayGradeRequest sang PayGrade
		return paygradeRepository.save(paygrade); // Lưu vào cơ sở dữ liệu và trả về đối tượng PayGrade đã lưu
	}

	// Get all PayGrades
	public List<Paygrade> getAll() {
		return paygradeRepository.findAll(); // Lấy tất cả các PayGrade từ cơ sở dữ liệu
	}

	// Get PayGrade by ID
	public Paygrade getById(Integer id) {
		Optional<Paygrade> paygrade = paygradeRepository.findById(id); // Tìm kiếm PayGrade theo ID
		return paygrade.orElse(null); // Nếu tìm thấy trả về, nếu không trả về null
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
		if (paygradeRepository.existsById(id)) { // Kiểm tra xem PayGrade có tồn tại không
			paygradeRepository.deleteById(id); // Xóa PayGrade
		}
	}
}
