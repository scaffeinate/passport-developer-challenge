package com.passport.project.factory.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passport.project.factory.bo.ResponseObject;
import com.passport.project.factory.domain.Factory;
import com.passport.project.factory.dto.FactoryDTO;
import com.passport.project.factory.exceptions.FactoryNotFoundException;
import com.passport.project.factory.exceptions.InvalidFactoryException;
import com.passport.project.factory.exceptions.InvalidNumChildNodesException;
import com.passport.project.factory.exceptions.UnprocessableException;
import com.passport.project.factory.services.FactoriesService;

@Controller
@RequestMapping("/factories")
public class FactoriesController {

	@Autowired
	private FactoriesService factoriesService;

	private Logger logger = Logger.getLogger(FactoriesController.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public ResponseEntity<ResponseObject[]> getFactories() throws JsonProcessingException {
		logger.info("GET /factories");

		List<Factory> factoriesList = new ArrayList<Factory>();
		logger.info("Call factoriesService.getFactories()");

		factoriesList = factoriesService.getFactories();
		logger.info("Size of factoriesList: " + factoriesList.size());

		ResponseObject[] responseObjects = new ResponseObject[1];
		responseObjects[0] = new ResponseObject(factoriesList);
		ResponseEntity<ResponseObject[]> responseEntity = new ResponseEntity<ResponseObject[]>(responseObjects,
				HttpStatus.OK);
		logger.info("Response Object Body: " + objectMapper.writeValueAsString(responseEntity.getBody()));

		return responseEntity;
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST)
	public ResponseEntity<Factory> createFactory(@RequestBody(required = true) FactoryDTO factory)
			throws InvalidFactoryException, UnprocessableException, JsonProcessingException {
		logger.info("POST /factories");
		
		logger.info("Factory Object: " + objectMapper.writeValueAsString(factory));
		logger.info("Call factoriesService.createFactory(factory)");
		Factory createdFactory = factoriesService.createFactory(factory);

		ResponseEntity<Factory> responseEntity = new ResponseEntity<Factory>(createdFactory, HttpStatus.CREATED);
		logger.info("Response Object Body: " + objectMapper.writeValueAsString(responseEntity.getBody()));

		return responseEntity;
	}

	@RequestMapping(value = { "/{factoryId}", "/{factoryId}/" }, method = RequestMethod.PUT)
	public ResponseEntity<Factory> updateFactory(@RequestBody(required = true) FactoryDTO factory,
			@PathVariable String factoryId)
			throws InvalidFactoryException, UnprocessableException, FactoryNotFoundException, JsonProcessingException {
		logger.info("PUT /factories/" + factoryId);

		logger.info("Factory Object: " + objectMapper.writeValueAsString(factory));
		logger.info("Call factoriesService.updateFactory(factory, factoryId)");
		Factory updatedFactory = factoriesService.updateFactory(factory, factoryId);
		
		ResponseEntity<Factory> responseEntity = new ResponseEntity<Factory>(updatedFactory, HttpStatus.OK);
		logger.info("Response Object Body: " + objectMapper.writeValueAsString(responseEntity.getBody()));
		
		return responseEntity;
	}

	@RequestMapping(value = { "/{factoryId}", "/{factoryId}/" }, method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteFactory(@PathVariable String factoryId)
			throws FactoryNotFoundException, UnprocessableException {
		logger.info("DELETE /factories" + factoryId);
		
		logger.info("Call factoriesService.deleteFactory(factoryId)");
		factoriesService.deleteFactory(factoryId);
		
		logger.info("Deleted Factory " + factoryId);
		
		ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = { "/{factoryId}/childNodes", "/{factoryId}/childNodes/" }, method = RequestMethod.POST)
	public ResponseEntity<Factory> createChildNodes(@PathVariable String factoryId,
			@RequestParam(required = true) Integer numChildNodes)
			throws FactoryNotFoundException, UnprocessableException, InvalidNumChildNodesException, JsonProcessingException {
		logger.info("POST /factories/" + factoryId + "/childNodes");
		logger.info("numChildNodes: " + numChildNodes);
		
		logger.info("Call factoriesService.createChildNodes(numChildNodes, factoryId)");
		Factory factory = factoriesService.createChildNodes(numChildNodes, factoryId);
		
		ResponseEntity<Factory> responseEntity = new ResponseEntity<Factory>(factory, HttpStatus.OK);
		logger.info(objectMapper.writeValueAsString(responseEntity.getBody()));
		
		return responseEntity;
	}
}
