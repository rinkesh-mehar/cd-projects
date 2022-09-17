package com.drkrishi.prm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EntityScan(basePackages = { "com.drkrishi.prm.entity", "com.drkrishi.prm.newentity" })
@EnableSwagger2
//@EnableEurekaClient
public class PrmApplication {

	private static final Logger lOGGER = LogManager.getLogger(PRMController.class);
	
	public static void main(String[] args) {
		SpringApplication.run(PrmApplication.class, args);
		lOGGER.info("Server started successfully");
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
}
