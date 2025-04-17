package com.example.Humosoft.Mapper;

import java.net.PasswordAuthentication;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.UserRequest;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.Address;
import com.example.Humosoft.Model.Role;
import com.example.Humosoft.Model.Position;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.DepartmentRepository;
import com.example.Humosoft.Repository.PositionRepository;
import com.example.Humosoft.Repository.RoleRepository;
import com.example.Humosoft.Repository.UserRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Builder
public class UserMapper {
	private final RoleRepository roleRepository;
	private final PositionRepository positionRepository;
	private final DepartmentRepository departmentRepository;
	private final UserRepository userRepository;
	// Phương thức chuyển đổi từ UserRequest sang User

	public User toUser(UserRequest userRequest) {
		// Tạo đối tượng Address từ UserRequest
		Address address = new Address();
		address.setHouseNumber(userRequest.getHouseNumber());
		address.setStreet(userRequest.getStreet());
		address.setCity(userRequest.getCity());
		address.setCommune(userRequest.getCommune());
		address.setDistrict(userRequest.getDistrict());
		address.setState(userRequest.getState());
		address.setPostalCode(userRequest.getPostalCode());
		address.setCountry(userRequest.getCountry());

		// Tạo đối tượng User và ánh xạ các giá trị
		User user = new User();
		boolean gender=userRequest.getGender().equals("Male")?true:false;
		user.setFullName(userRequest.getFullName());
		user.setEmail(userRequest.getEmail());
		user.setPhone(userRequest.getPhone());
		user.setDateOfBirth(userRequest.getDateOfBirth());
		user.setGender(gender);
		user.setImage(userRequest.getImage());
		user.setCreatedAt(new Date());
		user.setStatus(userRequest.isStatus());
		user.setAddress(address); // Gán địa chỉ vào người dùng
		Role user_role = roleRepository.findByName("ROLE_USER")
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));
		Set<Role> roles = new HashSet<>();
		roles.add(user_role);
		user.setRole(roles);
		Department department = departmentRepository.findByDepartmentName(userRequest.getDepartmentName())
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
		Position position = positionRepository.findByPositionName(userRequest.getPositionName())
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.PAYGRADE_NOT_FOUND));
		user.setPosition(position);
		user.setDepartment(department);
		user.setUsername(userRequest.getUsername());
		return user; // Trả về đối tượng User đã được tạo và ánh xạ
	}

	public UserResponse toUserResponse(User user) {
	    Department department = user.getDepartment();
	    User manager = null;

	    // Kiểm tra department có tồn tại không trước khi lấy managerId
	    if (department != null && department.getManagerId() != null) {
	    	 manager = userRepository.findById(department.getManagerId()).orElse(null);
	    }

	    return UserResponse.builder()
	            .fullName(user.getFullName())
	            .email(user.getEmail())
	            .phone(user.getPhone())
	            .id(user.getId())
	            .dateOfBirth(user.getDateOfBirth())
	            .gender(user.isGender() ? "Male" : "Female")
	            .positionName(user.getPosition() != null ? user.getPosition().getPositionName() : null)
	            .departmentName(department != null ? department.getDepartmentName() : null)
	            .status(user.isStatus())
	            .managerName(manager != null ? manager.getFullName() : null)
	            .createAt(user.getCreatedAt())
	            .username(user.getUsername())
	            .build();
	}

}
