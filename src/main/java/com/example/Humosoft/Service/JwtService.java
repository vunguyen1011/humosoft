package com.example.Humosoft.Service;


import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepository;

    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${spring.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    // Tạo access token
    public String generateAccessToken(String username) {
        return buildToken(username, jwtExpiration);
    }

    // Tạo refresh token
    public String generateRefreshToken(String username) {
        return buildToken(username, refreshExpiration);
    }
    // tạo token quên mật khẩu 
    public String generateForgotPassword(String username) {
    	return buildToken(username, 900000);
    }
    // Xây dựng token
    private String buildToken(String username, long time) {
        System.out.println(username);

   
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new WebErrorConfig(ErrorCode.ROLE_NOT_FOUND));

        return Jwts.builder()
                .setSubject(username) // Subject là tên người dùng
                .setIssuer("NguyenVu")
                .setIssuedAt(new Date()) // Thời gian phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + time)) // Token hết hạn sau 1 giờ
                .claim("role", user.getRole()) // Thêm claim "role" với các vai trò của người dùng
               .signWith(getKey())
                .compact();
    }

    // Lấy Key từ secret key
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractbyUsername(String token) {
    	return extractClaim(token, Claims::getSubject);
    	
    }
    private Claims extractAllClaim(String token) {
    	return Jwts.parserBuilder()
    			.setSigningKey(getKey())
    			.build()
    			.parseClaimsJws(token)
    			.getBody();
    }
    private <T> T extractClaim(String token,Function<Claims,T>claimReslover) {
    	Claims claims=extractAllClaim(token);
    	return claimReslover.apply(claims);
    	
    }
    private boolean isTokenExpired(String token) {
        return extractDateToken(token).before(new Date());
    }

    private Date extractDateToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

  

    public boolean isValid(String token, User user) {
        String username = extractbyUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token) && isChanged(token);
    }

    public boolean isChanged(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    
}
