package com.example.Humosoft.Exception;

public enum ErrorCode {

	USER_NOT_FOUND(1001, "Không thấy người dùng đâu cả"),
	DEPARTMENT_NOT_FOUND(1002,"Phòng ban này không tồn tại"),
	PAYGRADE_NOT_FOUND(1003,"Mức lương này không tồn tại "),
	POSITION_NOT_FOUND(1004,"Vị trí này không tồn tại"),
	ROLE_NOT_FOUND(1005,"Role này không tồn tại"),
	PAYGRADE_ALREADY_EXISTS(1007,"Mức lương này đã tồn tại"),
	POSITION_ALREADY_EXISTS(1008,"Vị trí này đã tồn tại"),
	INVALID_CREDENTIALS(1009,"Tài khoản hoặc mật khẩu không chính xác"),
	DEPARTMENT_ALREADY_EXISTS(1006,"Phòng ban này đã tồn tại"),
	INVALID_DATE_RANGE(1017,"Thiết lập ngày không hợp lệ"),
	EXCEEDS_ONE_MONTH_LIMIT(1022,"quá tháng rồi em ơi 1 tháng thôi"),
	NO_USERS_IN_DEPARTMENT(1019,"Không có nhân viên nào trong phòng ban này"),
	OLD_PASSWORD_SAME(1011,"Không thể đặt lại mật khẩu giống mật khẩu cũ"),
	USER_ALREADY_IN_DEPARTMENT(1012,"Nhân viên này đã trong phòng"),
	USER_ALREADY_IN_ANOTHER_DEPARTMENT(1013,"Nhân viên này đã trong phòng ban khác"),
	USER_NOT_IN_DEPARTMENT(1014,"Nhân viên không thuộc phòng ban này "),
	USER_ALREADY_MANAGER(1015,"Đã là trưởng ban của phòng khác"),
	NO_MANAGER_FOUND(1016,"Phòng này không có trưởng phòng"),
	TIMESHEET_ALREADY_EXISTS(1023,"TimeSheet này đã tồn tại "),
	TIMESHEET_DATE_OVERLAP(1024,"Thời gian bị trùng"),
	ATTENDANCE_NOT_FOUND(1025,"không thấy ngày chấm công này"),
	NO_DEPARTMENTS_FOUND(1028,"Không có phòng ban nào cả"),
	EMAIL_ALREADY_EXISTS(1030,"Email này đã tồn tại"),
	PHONE_ALREADY_EXISTS(1031,"SDT này đã tồn tại"),
	USERNAME_ALREADY_EXISTS(1032,"Username này đã tồn tại"),
	ALREADY_CHECKED_IN(1026,"bạn đã chấm công rồi"),
	TIMEOFF_NOT_FOUND(1035,"Không có đơn nghỉ phép này"),
	 LEAVE_TYPE_NOT_FOUND(1033,"Loại nghỉ phép không hợp lệ!"),
	 TIMESHEET_NOT_FOUND(1036,"Không tìm thấy timesheet này"),
	 SUBTASK_NOT_FOUND(1037,"Không tìm thấy subtask này"),
	 RECRUITMENT_NOT_FOUND(1038,"Không tìm thấy tuyển dụng này"),
//	    INVALID_DATE_RANGE(1034,"Ngày kết thúc không thể trước ngày bắt đầu!"),
	    TIMEOFF_OVERLAP(1031,"Đã có đơn nghỉ phép trong khoảng thời gian này!"),
	TASK_ALREADY_EXISTS(1040,"Task này đã tồn tại "),
	TASK_NOT_FOUND(1041,"Task not found"),
	ALREADY_CHECKED_OUT(1027,"Bạn đã check out rồi"),
	PASSWORD_MISMATCH(1010,"Mật khẩu không giống nhau"),
	APPLICATION_NOT_FOUND(1042,"Không tìm thấy đơn ứng tuyển này"),
	INTERVIEW_ALREADY_EXISTS(1050,"Interview này đã tồn tại "),
	INTERVIEW_NOT_FOUND(1042,"Không tìm thấy interview này");
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
