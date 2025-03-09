package com.example.Humosoft.Service;

import org.apache.catalina.startup.WebAnnotationSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.Humosoft.Exception.ErrorCode;
import com.example.Humosoft.Exception.WebErrorConfig;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
	@Value("${spring.mail.from}")
	private String emailFrom; // Lấy thông tin email từ application.properties

	private final JavaMailSender mailSender;
	private  final JwtService jwtService;
	private final UserRepository userRepository;

	// Phương thức gửi email với username, password và message
	public String sendEmail(String toEmail, String username, String password) throws MessagingException {

		// Tạo đối tượng MimeMessage
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		// Cấu hình email
		helper.setFrom(emailFrom); // Email người gửi
		helper.setTo(toEmail); // Email người nhận

		// Tiêu đề email
		String subject = "Your Account Details";

		// Nội dung email
		String content = "<h3>Account Details</h3>" + "<p>Username: " + username + "</p>" + "<p>Password: " + password
				+ "</p>" + "<p>Message: " + "Chào mừng bạn đến với công ty Humosoft" + "</p>";

		// Thiết lập tiêu đề và nội dung email
		helper.setSubject(subject);
		helper.setText(content, true); // true để bật chế độ HTML

		// Gửi email
		mailSender.send(message);

		log.info("Email sent successfully.");
		return "sent"; // Trả về kết quả gửi thành công
	}

	public void sendRequestForgotPassword(String toEmail, String link) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		User user=userRepository.findByEmail(toEmail).orElseThrow(()->new WebErrorConfig(ErrorCode.USER_NOT_FOUND));
		String token =jwtService.generateForgotPassword(user.getUsername());
		String linkChangePassword=link+"?token="+token;
		helper.setFrom(emailFrom);
		helper.setTo(toEmail);

		// Tiêu đề email
		String subject = "Yêu cầu đặt lại mật khẩu";

		// Nội dung email
		String content = "<p>Xin chào,</p>" + "<p>Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản của mình.</p>"
				+ "<p>Nhấn vào liên kết bên dưới để đặt lại mật khẩu:</p>" + "<p><a href=\"" + linkChangePassword
				+ "\">Đặt lại mật khẩu</a></p>" + "<br>" + "<p>Nếu bạn không yêu cầu, vui lòng bỏ qua email này.</p>";

		// Thiết lập nội dung email
		helper.setSubject(subject);
		helper.setText(content, true); // Cho phép HTML

		// Gửi email
		mailSender.send(message);

		log.info("Email đặt lại mật khẩu đã được gửi đến: {}", toEmail);
	}

}
