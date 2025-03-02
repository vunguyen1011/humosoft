package com.example.Humosoft.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.Humosoft.DTO.Response.Apiresponse;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(value = WebErrorConfig.class)
	ResponseEntity<Apiresponse> handlerRunTimeException(WebErrorConfig exception) {
		ErrorCode errorCode = exception.getErrorCode();

		return ResponseEntity.badRequest()
				.body(Apiresponse.builder().code(errorCode.getCode()).message(errorCode.getMessage())

						.build()

				);
	}
}
