package com.passport.project.factory.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.passport.project.factory.dto.FactoryDTO;
import com.passport.project.factory.exceptions.InvalidFactoryException;

@Service
public class ValidatorService {
	
	private Logger logger = Logger.getLogger(ValidatorService.class);
	
	public void validateFactory(FactoryDTO factory) throws InvalidFactoryException {
		
		String factoryName = StringUtils.trimWhitespace(factory.getFactoryName());
		Integer lowerBound = factory.getLowerBound();
		Integer upperBound = factory.getUpperBound();
		
		logger.info("Factory Name: " + factoryName);
		logger.info("Lower Bound: " + lowerBound);
		logger.info("Upper Bound: " + upperBound);
		
		if(StringUtils.isEmpty(factoryName)) {
			throw new InvalidFactoryException("Factory name is blank.");
		}
		
		if(factoryName.length() > 50) {
			throw new InvalidFactoryException("Factory name is longer than 50 characters.");
		}
		
		if(lowerBound == null) {
			throw new InvalidFactoryException("Lower Bound value is blank.");
		}
		
		if(upperBound == null) {
			throw new InvalidFactoryException("Upper Bound value is blank.");
		}
		
		if(lowerBound < 0) {
			throw new InvalidFactoryException("Lower Bound lesser than zero.");
		}
		
		if(upperBound < 0) {
			throw new InvalidFactoryException("Upper Bound lesser than zero.");
		}
		
		if(lowerBound > 99999999) {
			throw new InvalidFactoryException("Lower Bound greater than maximum value.");
		}
		
		if(upperBound > 99999999) {
			throw new InvalidFactoryException("Upper Bound greater than maximum value.");
		}
		
		if(lowerBound >= upperBound) {
			throw new InvalidFactoryException("LowerBound should be less than UpperBound.");
		}
	}
}
