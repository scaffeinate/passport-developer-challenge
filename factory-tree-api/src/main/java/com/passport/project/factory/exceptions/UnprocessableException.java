package com.passport.project.factory.exceptions;

import org.springframework.http.HttpStatus;

public class UnprocessableException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8004931192710087577L;
	private String message;
	private HttpStatus httpStatus;
	
	public UnprocessableException(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
		this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}	
}
