package com.example.Humosoft.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.DepartmentMapper;
import com.example.Humosoft.Mapper.UserMapper;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.DepartmentRepository;
import com.example.Humosoft.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final DepartmentMapper departmentMapper;

	private final UserService userService;
	

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	 public Department createDepartment(DepartmentRequest departmentRequest) {
		 	if(departmentRepository.existsByDepartmentName(departmentRequest.getDepartmentName()))
		 	{
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
	            updatedDepartment.setId(existingDepartment.getId());  // Keep the same ID
	            return departmentRepository.save(updatedDepartment);
	        }
	        return null;  // Or throw an exception if department is not found
	    }
	 public Department getDepartmentById(Integer id) {
	        return departmentRepository.findById(id).orElseThrow(()->new WebErrorConfig(ErrorCode.DEPARTMENT_NOT_FOUND));
	        
	    }
	 public List<Department> getAll(){
		 return departmentRepository.findAll();
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
		    User user = userRepository.findById(userId)
		            .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

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

	 public UserResponse deleteEmployee(Integer userId, Integer departmentId) {
		    // Tìm nhân viên theo ID
		    User user = userRepository.findById(userId)
		            .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

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

		    // Trả về thông tin nhân viên sau khi xóa khỏi phòng ban
		    return userMapper.toUserResponse(user);
		}


	 
}
