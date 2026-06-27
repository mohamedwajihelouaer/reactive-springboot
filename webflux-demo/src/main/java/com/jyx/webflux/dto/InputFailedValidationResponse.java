package com.jyx.webflux.dto;


public class InputFailedValidationResponse {
	
	
	private Integer errCode;
	private Integer input;
	private String message;
	
	
	public Integer getErrCode() {
		return errCode;
	}
	public Integer getInput() {
		return input;
	}
	public String getMessage() {
		return message;
	}
	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}
	public void setInput(Integer input) {
		this.input = input;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
