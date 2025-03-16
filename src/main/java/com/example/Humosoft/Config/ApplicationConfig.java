package com.example.Humosoft.Config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.Humosoft.Model.LeaveType;
import com.example.Humosoft.Model.Role;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.LeaveTypeRepository;
import com.example.Humosoft.Repository.RoleRepository;
import com.example.Humosoft.Repository.UserRepository;
import java.util.Set;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ApplicationRunner applicationRunner(
            RoleRepository roleRepository,
            UserRepository userRepository,
            LeaveTypeRepository leaveTypeRepository,  // Thêm repository này
            PasswordEncoder passwordEncoder) {
        return args -> {

            // Tạo Role nếu chưa có
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_ADMIN");
                return roleRepository.save(role);
            });

            Role managerRole = roleRepository.findByName("ROLE_MANAGER").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_MANAGER");
                return roleRepository.save(role);
            });

            Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_USER");
                return roleRepository.save(role);
            });

            // Kiểm tra nếu chưa có Admin thì tạo tài khoản mặc định
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // Mật khẩu đã mã hóa
                admin.setFullName("Administrator");
                admin.setEmail("vunguyen10112k4@gmail.com");
                admin.setPhone("0123456789");

                admin.setRole(Set.of(adminRole)); // Gán quyền Admin
                userRepository.save(admin);
                System.out.println("Admin account created: username=admin, password=admin123");
            }

            // 🛠 Tạo sẵn các loại nghỉ phép nếu chưa tồn tại
            createLeaveTypeIfNotExists(leaveTypeRepository, "Nghỉ phép năm", "Nghỉ hưởng nguyên lương", 12, true);
            createLeaveTypeIfNotExists(leaveTypeRepository, "Nghỉ ốm", "Nghỉ có giấy khám bệnh", 5, true);
            createLeaveTypeIfNotExists(leaveTypeRepository, "Nghỉ thai sản", "Dành cho nhân viên nữ", 180, true);
            createLeaveTypeIfNotExists(leaveTypeRepository, "Nghỉ không lương", "Nghỉ cá nhân không hưởng lương", 10, false);
        };
    }

    // Hàm tạo LeaveType nếu chưa tồn tại
    private void createLeaveTypeIfNotExists(
            LeaveTypeRepository leaveTypeRepository,
            String name, String description, int maxDays, boolean isPaid) {
        if (leaveTypeRepository.findByLeaveTypeName(name).isEmpty()) {
            LeaveType leaveType = new LeaveType();
            leaveType.setLeaveTypeName(name);
            leaveType.setDescription(description);
            leaveType.setMaxDays(maxDays);
            leaveType.setPaid(isPaid);
            leaveTypeRepository.save(leaveType);
            System.out.println("Created LeaveType: " + name);
        }
    }
}
