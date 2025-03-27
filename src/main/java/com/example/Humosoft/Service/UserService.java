package com.example.Humosoft.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.*;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Mapper.UserMapper;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.DepartmentRepository;
import com.example.Humosoft.Repository.UserRepository;

import jakarta.mail.MessagingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Data	
public class UserService {
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;
	private final DepartmentRepository departmentRepository;
	private final JwtService jwtService;

	public UserResponse create(UserRequest userRequest) {
		 if (userRepository.existsByEmail(userRequest.getEmail())) {
	            throw new WebErrorConfig(ErrorCode.EMAIL_ALREADY_EXISTS);
	        }

	        // Kiểm tra số điện thoại đã tồn tại chưa
	        if (userRepository.existsByPhone(userRequest.getPhone())) {
	            throw new WebErrorConfig(ErrorCode.PHONE_ALREADY_EXISTS);
	        }

	        
		User user = userMapper.toUser(userRequest);
		userRepository.save(user);
		return userMapper.toUserResponse(user);
	}

	public void createLogin(UserLogin userLogin) throws MessagingException {
		// Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(userLogin.getUsername())) {
            throw new WebErrorConfig(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
		User user = userRepository.findByEmail(userLogin.getEmail())
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
		user.setUsername(userLogin.getUsername());
		String password = passwordEncoder.encode(userLogin.getPassword());
		user.setPassword(password);
		userRepository.save(user);
		mailService.sendEmail(userLogin.getEmail(), userLogin.getUsername(), userLogin.getPassword());

	}

	public List<UserResponse> getAll() {
	    return userRepository.findByDeletedFalse()
	                         .stream()
	                         .map(userMapper::toUserResponse)
	                         .collect(Collectors.toList());
	}

	public User findUerById(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
	}

	

	public UserResponse findUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
		return userMapper.toUserResponse(user);
	}

	public List<UserResponse> findUser(String request) {
		return userRepository.findByFullNameOrEmailOrPhoneOrDepartment(request).stream().map(userMapper::toUserResponse).toList();
	}

	public User findUserByEmail(String mail) {
		return userRepository.findByEmail(mail).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
	}

	public void forgotPassword(ForgotPasswordRequest changePassword) {
	    String username = jwtService.extractbyUsername(changePassword.getToken());
	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

	    // Kiểm tra xác nhận mật khẩu
	    if (!changePassword.getPassword().equals(changePassword.getConfirmPassword())) {
	        throw new WebErrorConfig(ErrorCode.PASSWORD_MISMATCH);
	    }

	    // Kiểm tra mật khẩu mới không trùng với mật khẩu cũ
	    if (passwordEncoder.matches(changePassword.getPassword(), user.getPassword())) {
	        throw new WebErrorConfig(ErrorCode.OLD_PASSWORD_SAME);
	    }

	    // Mã hóa mật khẩu mới
	    String newPassword = passwordEncoder.encode(changePassword.getPassword());

	    // Lưu lại mật khẩu cũ
	    user.setOldPassword(user.getPassword());
	    user.setPassword(newPassword);
	    
	    userRepository.save(user);
	}


	public void delete(int user_id) {

		User user = userRepository.findById(user_id).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

		user.setDeleted(true);

		userRepository.save(user);
	}
	public List<UserResponse> findManagers() {
	    return userRepository.findByRoleName("ROLE_MANAGER")
	                         .stream()
	                         .map(userMapper::toUserResponse)
	                         .collect(Collectors.toList());
	}
	public List<UserResponse> findAllEmpolyessNotInDepartment() {
	   return getAll().stream()
			   .filter(user->user.getDepartmentName()==null)
			   .collect(Collectors.toList());
	}
	
}
