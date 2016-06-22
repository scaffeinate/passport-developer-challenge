package com.passport.project.factory.exceptions;

import org.springframework.http.HttpStatus;

public class FactoryNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3586305062248737967L;
	private String message;
	private HttpStatus httpStatus;
	
	public FactoryNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
		this.httpStatus = HttpStatus.NOT_FOUND;
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
