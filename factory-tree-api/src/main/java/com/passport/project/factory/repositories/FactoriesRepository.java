package com.passport.project.factory.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.passport.project.factory.dto.FactoryDTO;

@Repository
public interface FactoriesRepository extends MongoRepository<FactoryDTO, String> {

}
