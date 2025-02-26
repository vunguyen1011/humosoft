//package com.example.Humosoft.Config;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.hibernate.annotations.Comment;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.example.Humosoft.Repository.UserRepository;
//import com.example.Humosoft.Service.JwtService;
//
//import io.micrometer.common.lang.NonNull;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtServie;
//    private final UserRepository userRepository;
//
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) { // kiểm tra request
//            System.out.println("Đã chạy đến đây");
//            filterChain.doFilter(request, response);
//
//            return;
//        }
//        jwt = authHeader.substring(7); // lấy token
//        String username = jwtServie.extractbyUsername(jwt);
//        var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("can not valid"));
//        if (jwtServie.isValid(jwt, user)) {
////
////            List<GrantedAuthority> authorities
////                    = user.getRole().stream()
////                            .map(role -> new SimpleGrantedAuthority(role.getName()))
////                            .collect(Collectors.toList());
////
////            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
////            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//        }
//        filterChain.doFilter(request, response);
//
//    }
//
//}
