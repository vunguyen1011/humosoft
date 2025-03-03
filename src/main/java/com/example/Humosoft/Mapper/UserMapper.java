package com.example.Humosoft.Mapper;

import java.net.PasswordAuthentication;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Request.UserRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.Address;
import com.example.Humosoft.Model.Role;
import com.example.Humosoft.Model.Position;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.RoleRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Builder
public class UserMapper {
	private final RoleRepository roleRepository;
    // Phương thức chuyển đổi từ UserRequest sang User
	private final PasswordEncoder passwordEncoder;
    public User toUser(UserRequest userRequest) {
    	String password=passwordEncoder.encode(userRequest.getPassword());
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
        user.setFullName(userRequest.getFullName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setGender(userRequest.getGender());
        user.setImage(userRequest.getImage());
        user.setUsername(userRequest.getUsername());
        user.setPassword(password);
        user.setStatus(userRequest.isStatus());
        user.setAddress(address);  // Gán địa chỉ vào người dùng
        Role user_role =roleRepository.findByName("ROLE_USER").orElseThrow(()->new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));
        user.setRole(Set.of(user_role));
        return user;  // Trả về đối tượng User đã được tạo và ánh xạ
    }
    
}
