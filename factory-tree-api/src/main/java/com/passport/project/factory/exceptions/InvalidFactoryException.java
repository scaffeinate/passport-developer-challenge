package com.passport.project.factory.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidFactoryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8670168634382729489L;
	private String message;
	private HttpStatus httpStatus;
	
	public InvalidFactoryException(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
		this.httpStatus = HttpStatus.BAD_REQUEST;
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
