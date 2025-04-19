package com.example.Humosoft.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.ForgotPassword;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.Service.MailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("mails")
public class MailController {
	private final MailService mailService;
	@PostMapping("/sendrequest")
	public Apiresponse<Void> sendRequestForgotPassword(@RequestBody ForgotPassword request) throws MessagingException{
		mailService.sendRequestForgotPassword(request.getToEmail(),request.getLink());
		return Apiresponse.<Void>builder()
				.message("Đã gửi link đến bạn")
				.build();
	}
}
