package com.example.Humosoft.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.ForgotPasswordRequest;
import com.example.Humosoft.DTO.Request.UserLogin;
import com.example.Humosoft.DTO.Request.UserRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	@PostMapping
	public Apiresponse<UserResponse> create( @Valid @RequestBody UserRequest request) {
		UserResponse user=userService.create(request);
		return Apiresponse.<UserResponse>builder()
				.result(user)
				.build();
		
	}
	@PostMapping("/create")
	public Apiresponse<Void>createLogin(@RequestBody UserLogin request) throws MessagingException{
		userService.createLogin(request);
		return Apiresponse.<Void>builder()
				.message("Login create success")
				.build();
		
	}
	@GetMapping
	public Apiresponse<List<UserResponse>> findAll(){
		List<UserResponse> users=userService.getAll();
		return Apiresponse.<List<UserResponse>>builder()
				.result(users)
				.build();
	}
	   // ✅ Tìm user theo ID (dùng RequestParam thay vì PathVariable)
    @GetMapping("/find/{id}")
    public Apiresponse<User> findUserById(@PathVariable Integer id) {
        User user = userService.findUerById(id);
        return Apiresponse.<User>builder()
                .result(user)
                .message("User found successfully")
                .build();	
    }
    
    // ✅ Tìm user trong một phòng ban
   

    // ✅ Tìm user theo username
    @GetMapping("/searchByUsername")
    public Apiresponse<UserResponse> findUserByUsername(@RequestParam String username) {
        UserResponse user = userService.findUserByUsername(username);
        return Apiresponse.<UserResponse>builder()
                .result(user)
                .message("User found successfully")
                .build();
    }

    // ✅ Tìm user theo fullname hoặc email
    @GetMapping("/search")
    public Apiresponse<List<UserResponse>> findUser(@RequestParam String query) {
        List<UserResponse> users = userService.findUser(query);
        return Apiresponse.<List<UserResponse>>builder()
                .result(users)
                .message("User search results")
                .build();
    }
    @PostMapping("/forgot-password")
    public Apiresponse<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request);
        return Apiresponse.<Void>builder()
                .message("Password reset successfully")
                .build();
    }
    @GetMapping("/managers")
    public Apiresponse<List<UserResponse>> getManagers() {
        List<UserResponse> managers = userService.findManagers();
        return Apiresponse.<List<UserResponse>>builder()
                .result(managers)
                .build();
    }
    @GetMapping("/not-in-department")
    public Apiresponse<List<UserResponse>> getUserNotInDepartment() {
        List<UserResponse> userNotInDepartment = userService.findAllEmpolyessNotInDepartment();
        return Apiresponse.<List<UserResponse>>builder()
                .result(userNotInDepartment)
                .build();
    }

}
