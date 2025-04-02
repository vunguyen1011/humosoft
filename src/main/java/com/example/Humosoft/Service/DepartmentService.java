package com.example.Humosoft.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.DepartmentAddEmployeesRequest;
import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.DTO.Response.DepartmentResponse;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.DepartmentMapper;
import com.example.Humosoft.Mapper.UserMapper;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.Role;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.DepartmentRepository;
import com.example.Humosoft.Repository.RoleRepository;
import com.example.Humosoft.Repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final DepartmentMapper departmentMapper;

	private final UserService userService;

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final RoleRepository roleRepository;

	public Department createDepartment(DepartmentRequest departmentRequest) {
		if (departmentRepository.existsByDepartmentName(departmentRequest.getDepartmentName())) {
			throw new WebErrorConfig(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
		}
		Department department = departmentMapper.toDepartment(departmentRequest);
		return departmentRepository.save(department);
	}

	public Department updateDepartment(Integer id, DepartmentRequest departmentRequest) {
		Optional<Department> existingDepartmentOpt = departmentRepository.findById(id);
		if (existingDepartmentOpt.isPresent()) {
			Department existingDepartment = existingDepartmentOpt.get();
			Department updatedDepartment = departmentMapper.toDepartment(departmentRequest);
			updatedDepartment.setId(existingDepartment.getId()); // Keep the same ID
			return departmentRepository.save(updatedDepartment);
		}
		return null; // Or throw an exception if department is not found
	}
	   @Transactional
	public DepartmentResponse getDepartmentById(Integer id) {
		Department department= departmentRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
	    Hibernate.initialize(department.getTasks()); // Initialize the tasks collection
		return departmentMapper.toResponse(department);
	}
	public DepartmentResponse getDepartmentByName(String name ) {
		Department department= departmentRepository.findByDepartmentName(name).orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
		return departmentMapper.toResponse(department);

	}

	public List<DepartmentResponse> getAll() {
	    return departmentRepository.findAll().stream()
	            .map(dp -> departmentMapper.toResponse(dp)) // Sử dụng lambda
	            .collect(Collectors.toList());
	}


	public void delete(Integer id) {
		Department department = departmentRepository.findById(id)	
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		// Xóa mềm phòng ban
		department.setDeleted(true);
		departmentRepository.save(department);

		// Cập nhật department_id của nhân viên về null
		List<User> users = userRepository.findByDepartment(department);
		for (User user : users) {
			user.setDepartment(null);
		}
		userRepository.saveAll(users);
	}

	public List<UserResponse> findUserInDepartment(String departmentName) {
		Department department = departmentRepository.findByDepartmentName(departmentName)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		List<User> users = userRepository.findByDepartmentAndDeletedFalse(department);

		return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
	}

	public Integer numberEmployee(String departmentName) {
		return findUserInDepartment(departmentName).size();
	}

	public UserResponse addEmployee(Integer userId, Integer departmentId) {
		// Tìm phòng ban theo ID
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		// Tìm nhân viên theo ID
		User user = userRepository.findById(userId).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

		// Kiểm tra nếu nhân viên đã thuộc phòng ban (cùng hoặc khác)
		if (user.getDepartment() != null) {
			if (user.getDepartment().getId().equals(departmentId)) {
				// Nhân viên đã thuộc phòng ban này => Ném lỗi
				throw new WebErrorConfig(ErrorCode.USER_ALREADY_IN_DEPARTMENT);
			} else {
				// Nhân viên thuộc phòng ban khác => Ném lỗi
				throw new WebErrorConfig(ErrorCode.USER_ALREADY_IN_ANOTHER_DEPARTMENT);
			}
		}

		// Nếu nhân viên chưa có phòng ban, tiến hành thêm vào
		user.setDepartment(department);
		userRepository.save(user);

		departmentRepository.save(department);

		// Chuyển đổi sang DTO và trả về
		return userMapper.toUserResponse(user);
	}

	public void deleteEmployee(Integer userId, Integer departmentId) {
		// Tìm nhân viên theo ID
		User user = userRepository.findById(userId).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

		// Kiểm tra xem nhân viên có thuộc phòng ban này không
		if (user.getDepartment() == null || !user.getDepartment().getId().equals(departmentId)) {
			throw new WebErrorConfig(ErrorCode.USER_NOT_IN_DEPARTMENT);
		}

		// Tìm phòng ban theo ID
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		// Xóa nhân viên khỏi danh sách phòng ban

		departmentRepository.save(department);

		// Gán lại phòng ban của nhân viên thành null
		user.setDepartment(null);
		userRepository.save(user);

	}

	public void addManager(Integer userId, Integer departmentId) {
		// Tìm phòng ban theo ID
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		// Tìm nhân viên theo ID
		User newManager = userRepository.findById(userId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

		// Tìm vai trò "ROLE_MANAGER"
		Role roleManager = roleRepository.findByName("ROLE_MANAGER")
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));

		// Xoá vai trò "ROLE_MANAGER" của Trưởng phòng cũ (nếu có)
		if (department.getManagerId() != null) {
			User oldManager = userRepository.findById(department.getManagerId()).orElse(null);
			if (oldManager != null && oldManager.getRole().contains(roleManager)) {
				oldManager.getRole().remove(roleManager);
				userRepository.save(oldManager); // Lưu cập nhật vào DB
			}
		}

		// Kiểm tra nếu User mới đã là Trưởng phòng ở phòng ban khác
		if (departmentRepository.existsByManagerId(newManager.getId())) {
			throw new WebErrorConfig(ErrorCode.USER_ALREADY_MANAGER);
		}

		// Cập nhật Manager mới
		department.setManagerId(newManager.getId());

		// Nếu User mới chưa có quyền "ROLE_MANAGER", thêm vào
		if (!newManager.getRole().contains(roleManager)) {
			newManager.getRole().add(roleManager);
		}

		// Lưu thay đổi vào database
		userRepository.save(newManager);
		departmentRepository.save(department);
	}

	public void removeManager(Integer departmentId) {
		// Tìm phòng ban theo ID
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		// Kiểm tra xem phòng ban có Trưởng phòng không
		if (department.getManagerId() == null) {
			throw new WebErrorConfig(ErrorCode.NO_MANAGER_FOUND);
		}

		// Lấy thông tin Trưởng phòng hiện tại
		Integer managerId = department.getManagerId();
		User manager = userRepository.findById(managerId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

		// Xoá liên kết Trưởng phòng khỏi phòng ban
		department.setManagerId(null);

		// Tìm vai trò "ROLE_MANAGER"
		Role roleManager = roleRepository.findByName("ROLE_MANAGER")
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));

		manager.getRole().remove(roleManager);
		userRepository.save(manager); // Cập nhật User

		// Lưu thay đổi vào database
		departmentRepository.save(department);
	}
	public User getManager(String departmentName) {
		DepartmentResponse department=getDepartmentByName(departmentName);
		Integer  managerId=department.getManagerId();
		return  managerId==null?null:userService.findUerById(managerId);
	}
	public Department getDepartmentEntityById(Integer departmentId) {
	    return departmentRepository.findById(departmentId)
	            .orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
	}
	public Department getDepartmenEntitytByName(String name ) {
		return  departmentRepository.findByDepartmentName(name).orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
		

	}
	public List<UserResponse> addEmployees(DepartmentAddEmployeesRequest request) {
	    Department department = departmentRepository.findById(request.getDepartmentId())
	            .orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

	    List<User> users = userRepository.findAllById(request.getUserIds());

	    if (users.isEmpty()) {
	        throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
	    }

	    for (User user : users) {
	      addEmployee(user.getId(),department.getId());
	    }
	    return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
	}
	public List<UserResponse> removeEmployees(DepartmentAddEmployeesRequest request) {
	    Department department = departmentRepository.findById(request.getDepartmentId())
	            .orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

	    List<User> users = userRepository.findAllById(request.getUserIds());

	    if (users.isEmpty()) {
	        throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
	    }

	    for (User user : users) {
	       deleteEmployee(user.getId(), department.getId()); // Gọi hàm riêng để xử lý từng nhân viên
	    }

	 

	    return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
	}


}
