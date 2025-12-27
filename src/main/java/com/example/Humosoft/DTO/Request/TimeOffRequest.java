package com.example.Humosoft.DTO.Request;

import java.time.LocalDate;

import jakarta.validation.constraints.*; // Import bộ validation
import lombok.Data;

@Data
public class TimeOffRequest {

	@NotNull(message = "User ID không được để trống")
	@Positive(message = "User ID phải là số dương")
	private Integer userId;

	@NotNull(message = "Loại nghỉ phép (LeaveTypeId) không được để trống")
	@Positive(message = "ID loại nghỉ phép phải là số dương")
	private Integer leaveTypeId;

	@NotNull(message = "Ngày bắt đầu không được để trống")
	@FutureOrPresent(message = "Ngày bắt đầu phải là hôm nay hoặc trong tương lai")
	private LocalDate startDate;

	@NotNull(message = "Ngày kết thúc không được để trống")
	@Future(message = "Ngày kết thúc phải là một ngày trong tương lai")
	private LocalDate endDate;

	@NotBlank(message = "Lý do không được để trống")
	@Size(max = 255, message = "Lý do không được vượt quá 255 ký tự")
	private String reason;

	// --- VALIDATION NÂNG CAO: Kiểm tra Logic Ngày ---
	// Hibernate Validator không có sẵn check "EndDate > StartDate"
	// Ta dùng mẹo @AssertTrue để tự kiểm tra ngay trong DTO
	@AssertTrue(message = "Ngày kết thúc phải sau ngày bắt đầu")
	public boolean isEndDateAfterStartDate() {
		if (startDate == null || endDate == null) {
			return true; // Để @NotNull xử lý việc null
		}
		return endDate.isAfter(startDate) || endDate.isEqual(startDate);
	}
}