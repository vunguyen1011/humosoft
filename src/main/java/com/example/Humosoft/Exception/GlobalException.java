package com.example.Humosoft.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Apiresponse> handleValidationException(
			MethodArgumentNotValidException ex) {

		StringBuilder errorMessage = new StringBuilder();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errorMessage.append(error.getField())
					.append(": ")
					.append(error.getDefaultMessage())
					.append("; ");
		}

		return ResponseEntity.badRequest().body(
				Apiresponse.builder()
						.code(400)
						.message(errorMessage.toString())
						.build()
		);
	}
}
