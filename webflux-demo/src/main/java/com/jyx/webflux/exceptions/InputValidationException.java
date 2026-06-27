package com.jyx.webflux.exceptions;

public class InputValidationException extends RuntimeException {

	private static final String MSG = "Allowed Range is 10 - 20";
	private static final Integer ERR_CODE= -1;
	private final Integer input;
	
	public InputValidationException(Integer input) {
		super(MSG);
		this.input = input;
	}

	public   Integer getErrCode() {
		return ERR_CODE;
	}

	public Integer getInput() {
		return input;
	}
	
	
}
