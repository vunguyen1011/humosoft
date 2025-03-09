package com.example.Humosoft.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForgotPassword {
	String toEmail;
	String link;
}
