package com.passport.project.factory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passport.project.factory.domain.Factory;
import com.passport.project.factory.domain.FactoryMapper;
import com.passport.project.factory.domain.Node;
import com.passport.project.factory.dto.FactoryDTO;
import com.passport.project.factory.exceptions.FactoryNotFoundException;
import com.passport.project.factory.exceptions.InvalidFactoryException;
import com.passport.project.factory.exceptions.InvalidNumChildNodesException;
import com.passport.project.factory.exceptions.UnprocessableException;
import com.passport.project.factory.redis.RedisPublisher;
import com.passport.project.factory.repositories.FactoriesRepository;

@Service
public class FactoriesService {

	@Autowired
	private FactoriesRepository factoriesRepository;

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private FactoryMapper factoryMapper;

	@Autowired
	private RedisPublisher redisPublisher;

	private final String FACTORY_CREATE_CHANNEL = "factory-create";
	private final String FACTORY_UPDATE_CHANNEL = "factory-update";
	private final String FACTORY_DELETE_CHANNEL = "factory-delete";
	private final String FACTORY_CHILDNODES_CHANNEL = "factory-child";

	private Logger logger = Logger.getLogger(FactoriesService.class);

	public List<Factory> getFactories() {
		logger.info("getFactories() service");

		List<FactoryDTO> factoriesDTOList = factoriesRepository.findAll();
		List<Factory> factoriesList = new ArrayList<Factory>();

		logger.info("Factories fetched size: " + factoriesDTOList.size());

		for (FactoryDTO factoryDTO : factoriesDTOList) {
			Factory factory = factoryMapper.map(factoryDTO, Factory.class);
			factory.setNodes(factoryDTO.getChildren());
			factoriesList.add(factory);
		}

		return factoriesList;
	}

	public Factory createFactory(FactoryDTO factoryDTO) throws InvalidFactoryException, UnprocessableException {
		logger.info("createFactory() service");

		Factory createdFactory = new Factory();

		logger.info("Validating factory");
		validatorService.validateFactory(factoryDTO);

		FactoryDTO createdFactoryDTO = factoriesRepository.save(factoryDTO);

		if (createdFactoryDTO != null) {
			createdFactory = factoryMapper.map(createdFactoryDTO, Factory.class);
			createdFactory.setNodes(createdFactoryDTO.getChildren());
			logger.info("Created FactoryDTO and Mapped to factory");
		} else {
			throw new UnprocessableException("Unable to create Factory");
		}

		publishToRedis(createdFactory, FACTORY_CREATE_CHANNEL);

		return createdFactory;
	}

	public Factory updateFactory(FactoryDTO factoryDTO, String factoryId)
			throws InvalidFactoryException, UnprocessableException, FactoryNotFoundException {
		logger.info("updateFactory() service");

		boolean rangeChanged = false;

		Factory updatedFactory = new Factory();
		FactoryDTO existingFactoryDTO = findFactory(factoryId);

		if (factoryDTO.getFactoryName() != null) {
			existingFactoryDTO.setFactoryName(factoryDTO.getFactoryName());
		}

		if (factoryDTO.getLowerBound() != null) {
			if (factoryDTO.getLowerBound().intValue() != existingFactoryDTO.getLowerBound().intValue()) {
				rangeChanged = true;
			}
			existingFactoryDTO.setLowerBound(factoryDTO.getLowerBound());
		}

		if (factoryDTO.getUpperBound() != null) {
			if (factoryDTO.getUpperBound().intValue() != existingFactoryDTO.getUpperBound().intValue()) {
				rangeChanged = true;
			}
			existingFactoryDTO.setUpperBound(factoryDTO.getUpperBound());
		}

		if (rangeChanged) {
			existingFactoryDTO.setChildren(new ArrayList<Integer>());
		}

		logger.info("Validate Exsiting Factory DTO");
		validatorService.validateFactory(existingFactoryDTO);
		FactoryDTO updatedFactoryDTO = factoriesRepository.save(existingFactoryDTO);

		if (updatedFactoryDTO != null) {
			updatedFactory = factoryMapper.map(updatedFactoryDTO, Factory.class);
			updatedFactory.setNodes(updatedFactoryDTO.getChildren());
			if(rangeChanged) {
				updatedFactory.setRangeChanged(true);
			}
			logger.info("Updated FactoryDTO and mapped to Factory");
		} else {
			throw new UnprocessableException("Unable to update Factory");
		}

		publishToRedis(updatedFactory, FACTORY_UPDATE_CHANNEL);

		return updatedFactory;
	}

	public void deleteFactory(String factoryId) throws FactoryNotFoundException, UnprocessableException {
		logger.info("deleteFactory() service");

		FactoryDTO factoryDTO = findFactory(factoryId);
		factoriesRepository.delete(factoryId);

		Factory deletedFactory = new Factory();
		deletedFactory.setId(factoryId);
		deletedFactory.setText(factoryDTO.getFactoryName());
		logger.info("Deleted Factory: " + factoryId);

		publishToRedis(deletedFactory, FACTORY_DELETE_CHANNEL);
	}

	public Factory createChildNodes(Integer numChildNodes, String factoryId)
			throws FactoryNotFoundException, UnprocessableException, InvalidNumChildNodesException {
		// TODO Auto-generated method stub
		logger.info("createChildNodes() service");

		Factory updatedFactory = new Factory();

		FactoryDTO existingFactoryDTO = findFactory(factoryId);

		if (numChildNodes > 0 && numChildNodes <= 15) {
			List<Integer> childNodes = generateRandomNodes(numChildNodes, existingFactoryDTO);

			existingFactoryDTO.setChildren(childNodes);

			FactoryDTO updatedFactoryDTO = factoriesRepository.save(existingFactoryDTO);

			if (updatedFactoryDTO != null) {
				updatedFactory = factoryMapper.map(updatedFactoryDTO, Factory.class);
				updatedFactory.setNodes(existingFactoryDTO.getChildren());
				logger.info("Updated Factory with childNodes");
			} else {
				throw new UnprocessableException("Unable to add childNodes.");
			}
		} else {
			throw new InvalidNumChildNodesException("The Number of childNodes should be within 1 and 15.");
		}

		publishToRedis(updatedFactory, FACTORY_CHILDNODES_CHANNEL);

		return updatedFactory;
	}

	private void publishToRedis(Factory factory, String channel) {
		logger.info("Publish to Redis channel: " + channel);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String message = objectMapper.writeValueAsString(factory);
			logger.info("Redis Message: " + message);
			redisPublisher.publishMessage(channel, message);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
	}

	private FactoryDTO findFactory(String factoryId) throws FactoryNotFoundException {
		logger.info("Find Factory: " + factoryId);
		FactoryDTO factoryDTO = factoriesRepository.findOne(factoryId);

		if (factoryDTO == null) {
			throw new FactoryNotFoundException("Factory not found.");
		}

		return factoryDTO;
	}

	private List<Integer> generateRandomNodes(Integer numChildNodes, FactoryDTO existingFactoryDTO) {
		// TODO Auto-generated method stub
		Integer lowerBound = existingFactoryDTO.getLowerBound();
		Integer upperBound = existingFactoryDTO.getUpperBound();

		List<Integer> resultList = new ArrayList<Integer>();
		Random random = new Random();

		for (int i = 0; i < numChildNodes; i++) {
			resultList.add(random.ints(lowerBound, (upperBound + 1)).findFirst().getAsInt());
		}

		logger.info("Generated " + numChildNodes + " Random Nodes: " + resultList + " Within Range: [" + lowerBound
				+ " and " + upperBound + "]");

		return resultList;
	}
}
