package com.example.Humosoft.Service;

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
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {

	private final DepartmentRepository departmentRepository;
	private final DepartmentMapper departmentMapper;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final RoleRepository roleRepository;

	public Department createDepartment(DepartmentRequest request) {
		if (departmentRepository.existsByDepartmentName(request.getDepartmentName())) {
			throw new WebErrorConfig(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
		}
		return departmentRepository.save(departmentMapper.toDepartment(request));
	}

	public Department updateDepartment(Integer id, DepartmentRequest request) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
		if(departmentRepository.existsByDepartmentName(request.getDepartmentName()))
		{
			throw new WebErrorConfig(ErrorCode.DEPARTMENT_ALREADY_EXISTS);
		}

		department.setDepartmentName(request.getDepartmentName());
		department.setDescription(request.getDescription());

		return departmentRepository.save(department);
	}

	public DepartmentResponse getDepartmentById(Integer id) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		Hibernate.initialize(department.getTasks());
		return departmentMapper.toResponse(department);
	}

	public List<DepartmentResponse> getAll() {
		return departmentRepository.findAll()
				.stream()
				.map(departmentMapper::toResponse)
				.collect(Collectors.toList());
	}

	public void delete(Integer id) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		department.setDeleted(true);

		List<User> users = userRepository.findByDepartment(department);
		users.forEach(user -> user.setDepartment(null));

		userRepository.saveAll(users);
		departmentRepository.save(department);
	}

	public List<UserResponse> findUserInDepartment(String departmentName) {
		Department department = departmentRepository.findByDepartmentName(departmentName)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		return userRepository.findByDepartmentAndDeletedFalse(department)
				.stream()
				.map(userMapper::toUserResponse)
				.collect(Collectors.toList());
	}

	public List<UserResponse> addEmployees(DepartmentAddEmployeesRequest request) {

		if (request.getUserIds() == null || request.getUserIds().isEmpty()) {
			throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
		}

		Department department = departmentRepository.findById(request.getDepartmentId())
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		List<User> users = userRepository.findAllById(request.getUserIds());

		if (users.size() != request.getUserIds().size()) {
			throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
		}

		for (User user : users) {
			if (user.getDepartment() != null) {
				if (user.getDepartment().getId().equals(department.getId())) {
					throw new WebErrorConfig(ErrorCode.USER_ALREADY_IN_DEPARTMENT);
				}
				throw new WebErrorConfig(ErrorCode.USER_ALREADY_IN_ANOTHER_DEPARTMENT);
			}
		}

		users.forEach(user -> user.setDepartment(department));
		userRepository.saveAll(users);

		return users.stream()
				.map(userMapper::toUserResponse)
				.collect(Collectors.toList());
	}

	public List<UserResponse> removeEmployees(DepartmentAddEmployeesRequest request) {

		if (request.getUserIds() == null || request.getUserIds().isEmpty()) {
			throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
		}

		Department department = departmentRepository.findById(request.getDepartmentId())
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		List<User> users = userRepository.findAllById(request.getUserIds());

		if (users.size() != request.getUserIds().size()) {
			throw new WebErrorConfig(ErrorCode.USER_NOT_FOUND);
		}

		for (User user : users) {
			if (user.getDepartment() == null ||
					!user.getDepartment().getId().equals(department.getId())) {
				throw new WebErrorConfig(ErrorCode.USER_NOT_IN_DEPARTMENT);
			}
		}

		users.forEach(user -> user.setDepartment(null));
		userRepository.saveAll(users);

		return users.stream()
				.map(userMapper::toUserResponse)
				.collect(Collectors.toList());
	}

	public void addManager(Integer userId, Integer departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		User newManager = userRepository.findById(userId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

		Role roleManager = roleRepository.findByName("ROLE_MANAGER")
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));

		if (department.getManagerId() != null) {
			userRepository.findById(department.getManagerId()).ifPresent(oldManager -> {
				oldManager.getRole().remove(roleManager);
				userRepository.save(oldManager);
			});
		}

		if (departmentRepository.existsByManagerId(newManager.getId())) {
			throw new WebErrorConfig(ErrorCode.USER_ALREADY_MANAGER);
		}

		department.setManagerId(newManager.getId());

		if (!newManager.getRole().contains(roleManager)) {
			newManager.getRole().add(roleManager);
		}

		userRepository.save(newManager);
		departmentRepository.save(department);
	}

	public void removeManager(Integer departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));

		if (department.getManagerId() == null) {
			throw new WebErrorConfig(ErrorCode.NO_MANAGER_FOUND);
		}

		User manager = userRepository.findById(department.getManagerId())
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

		Role roleManager = roleRepository.findByName("ROLE_MANAGER")
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));

		manager.getRole().remove(roleManager);
		department.setManagerId(null);

		userRepository.save(manager);
		departmentRepository.save(department);
	}

	public Department getDepartmentEntityById(Integer departmentId) {
		return departmentRepository.findById(departmentId)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
	}
}
