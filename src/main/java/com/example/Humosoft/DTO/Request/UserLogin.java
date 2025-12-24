package com.example.Humosoft.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLogin {

	@Email(message = "Email không đúng định dạng")
	private String email;

	@NotBlank(message = "Username không được để trống")
	@Size(min = 6, max = 50, message = "Username phải từ 6 đến 50 ký tự")
	@Pattern(
			regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]+$",
			message = "Username phải chứa ít nhất 1 chữ cái và chỉ gồm chữ hoặc số"
	)
	private String username;

	@NotBlank(message = "Password không được để trống")
	@Size(min = 6, max = 100, message = "Password phải từ 6 đến 100 ký tự")
	private String password;

}
