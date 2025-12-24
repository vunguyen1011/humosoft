package com.example.Humosoft.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

	private String token;

	// 2. Validate Password: Không rỗng, độ dài tối thiểu 6
	@NotBlank(message = "Mật khẩu mới không được để trống")
	@Size(min = 6,max = 50, message = "Mật khẩu phải chứa ít nhất 6 ký tự")
	private String password;

	// 3. Validate ConfirmPassword: Chỉ cần check không rỗng
	@NotBlank(message = "Vui lòng nhập lại mật khẩu xác nhận")
	private String confirmPassword;
}
