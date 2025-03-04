package com.example.Humosoft.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Humosoft.DTO.Request.LoginRequest;
import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;  // Dịch vụ JWT
    private final UserRepository userRepository;  // Repository để truy xuất người dùng
    private final PasswordEncoder passwordEncoder;  // Bộ mã hóa mật khẩu

    // Phương thức đăng nhập
    public String signin(LoginRequest request) {
        // Tìm người dùng trong cơ sở dữ liệu theo tên đăng nhập
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));  // Nếu không tìm thấy người dùng, ném lỗi

        // Kiểm tra mật khẩu của người dùng có hợp lệ không
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WebErrorConfig(ErrorCode.INVALID_CREDENTIALS);  // Nếu mật khẩu không khớp, ném lỗi
        }

        // Tạo JWT token cho người dùng
        String accessToken = jwtService.generateAccessToken(user.getUsername());  // Tạo access token
        return accessToken;  // Trả về access token
    }
}
