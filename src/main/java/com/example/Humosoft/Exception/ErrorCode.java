package com.example.Humosoft.Exception;

public enum ErrorCode {

	USER_NOT_FOUND(1001, "Không thấy người dùng đâu cả"),
	DEPARTMENT_NOT_FOUND(1002,"Phòng ban này không tồn tại"),
	PAYGRADE_NOT_FOUND(1003,"Mức lương này không tồn tại "),
	POSITION_NOT_FOUND(1004,"Vị trí này không tồn tại"),
	ROLE_NOT_FOUND(1005,"Role này không tồn tại"),
	PAYGRADE_ALREADY_EXISTS(1007,"Role này không tồn tại"),
	POSITION_ALREADY_EXISTS(1008,"Vị trí này đã tồn tại"),
	DEPARTMENT_ALREADY_EXISTS(1006,"Phòng ban này đã tồn tại");
	private final int code;
	private final String message;

	// Constructor của enum để gán giá trị cho mỗi hằng số
	ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	// Các phương thức getter
	public int getCode() {
		return code;
	}
	

	public String getMessage() {
		return message;
	}
}
