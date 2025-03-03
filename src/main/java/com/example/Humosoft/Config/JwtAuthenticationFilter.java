package com.example.Humosoft.Config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Repository.UserRepository;
import com.example.Humosoft.Service.JwtService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Lấy Header Authorization từ request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        // Kiểm tra nếu Authorization không tồn tại hoặc không bắt đầu bằng "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Nếu không có header Authorization, chỉ cần tiếp tục filter chain
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy JWT từ Authorization header
        jwt = authHeader.substring(7);

        // Lấy tên người dùng từ JWT và tìm người dùng trong cơ sở dữ liệu
        String username = jwtService.extractbyUsername(jwt);
        var user = userRepository.findByUsername(username).orElseThrow(() -> new WebErrorConfig(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra xem token có hợp lệ không
        if (jwtService.isValid(jwt, user)) {

            // Nếu người dùng có vai trò, tạo danh sách quyền
            List<GrantedAuthority> authorities = user.getRole().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            // Tạo đối tượng authenticationToken với thông tin người dùng và quyền
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Lưu thông tin người dùng và quyền vào SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Tiếp tục filter chain để request tiếp tục đi vào hệ thống
        filterChain.doFilter(request, response);
    }
}
