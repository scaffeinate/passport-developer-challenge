package com.passport.project.factory.domain;

import org.springframework.stereotype.Component;

import com.passport.project.factory.dto.FactoryDTO;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class FactoryMapper extends ConfigurableMapper {
	protected void configure(MapperFactory factory) {
		factory.classMap(FactoryDTO.class, Factory.class).field("factoryName", "text").byDefault().register();
	}
}
