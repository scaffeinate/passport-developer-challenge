package com.passport.project.factory;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.passport.project.factory.bo.ErrorObject;
import com.passport.project.factory.exceptions.FactoryNotFoundException;
import com.passport.project.factory.exceptions.InvalidFactoryException;
import com.passport.project.factory.exceptions.InvalidNumChildNodesException;
import com.passport.project.factory.exceptions.UnprocessableException;

@ControllerAdvice
public class ExceptionAdvice {

	private Logger logger = Logger.getLogger(ExceptionAdvice.class);

	@ExceptionHandler(value = InvalidFactoryException.class)
	public ResponseEntity<ErrorObject> handleInvalidFactory(InvalidFactoryException invalidFactoryException) {
		logger.error("InvalidFactoryException");
		ErrorObject errorObject = new ErrorObject();

		errorObject.setHttpStatus(invalidFactoryException.getHttpStatus());
		errorObject.setMessage(invalidFactoryException.getMessage());

		logger.error("Message: " + invalidFactoryException.getMessage() + " & HttpStatus: "
				+ invalidFactoryException.getHttpStatus());

		return new ResponseEntity<ErrorObject>(errorObject, errorObject.getHttpStatus());
	}

	@ExceptionHandler(value = FactoryNotFoundException.class)
	public ResponseEntity<ErrorObject> handleFactoryNotFound(FactoryNotFoundException factoryNotFoundException) {
		logger.error("FactoryNotFoundException");
		ErrorObject errorObject = new ErrorObject();

		errorObject.setHttpStatus(factoryNotFoundException.getHttpStatus());
		errorObject.setMessage(factoryNotFoundException.getMessage());

		logger.error("Message: " + factoryNotFoundException.getMessage() + " & HttpStatus: "
				+ factoryNotFoundException.getHttpStatus());

		return new ResponseEntity<ErrorObject>(errorObject, errorObject.getHttpStatus());
	}

	@ExceptionHandler(value = UnprocessableException.class)
	public ResponseEntity<ErrorObject> handleFactoryNotFound(UnprocessableException unprocessableException) {
		logger.error("UnprocessableException");
		ErrorObject errorObject = new ErrorObject();

		errorObject.setHttpStatus(unprocessableException.getHttpStatus());
		errorObject.setMessage(unprocessableException.getMessage());

		logger.error("Message: " + unprocessableException.getMessage() + " & HttpStatus: "
				+ unprocessableException.getHttpStatus());

		return new ResponseEntity<ErrorObject>(errorObject, errorObject.getHttpStatus());
	}

	@ExceptionHandler(value = InvalidNumChildNodesException.class)
	public ResponseEntity<ErrorObject> handleInvalidNumChildNodes(
			InvalidNumChildNodesException invalidNumChildNodesException) {
		logger.error("InvalidNumChildNodesException");
		ErrorObject errorObject = new ErrorObject();

		errorObject.setHttpStatus(invalidNumChildNodesException.getHttpStatus());
		errorObject.setMessage(invalidNumChildNodesException.getMessage());

		logger.error("Message: " + invalidNumChildNodesException.getMessage() + " & HttpStatus: "
				+ invalidNumChildNodesException.getHttpStatus());

		return new ResponseEntity<ErrorObject>(errorObject, errorObject.getHttpStatus());
	}
}
