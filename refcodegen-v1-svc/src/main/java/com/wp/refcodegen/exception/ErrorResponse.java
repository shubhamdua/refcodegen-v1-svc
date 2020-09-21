 package com.wp.refcodegen.exception;

public class ErrorResponse {
	int status;
	String message;
	
	public ErrorResponse(){}
	
	public ErrorResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
