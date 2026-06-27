package com.jyx.webflux.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jyx.webflux.dto.InputFailedValidationResponse;

@ControllerAdvice
public class InputValidationExceptionHandler {

	@ExceptionHandler(InputValidationException.class)
	public ResponseEntity<InputFailedValidationResponse> handlerException(InputValidationException ex) {
		
		InputFailedValidationResponse response = new InputFailedValidationResponse();
		response.setErrCode(ex.getErrCode());
		response.setInput(ex.getInput());
		response.setMessage(ex.getMessage());

		return ResponseEntity.badRequest().body(response);
	}
}
