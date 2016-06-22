package com.passport.project.factory.bo;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorObject {

	@JsonIgnore
	private HttpStatus httpStatus;
	private String error;
	private String message;

	public ErrorObject() {
		
	}
	
	public String getStatus() {
		return this.httpStatus.toString();
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
		this.error = this.httpStatus.getReasonPhrase();
	}
}
