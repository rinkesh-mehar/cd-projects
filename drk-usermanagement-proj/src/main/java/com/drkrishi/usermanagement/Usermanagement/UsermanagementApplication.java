package com.drkrishi.usermanagement.Usermanagement;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EntityScan( basePackages = {"com.drkrishi.usermanagement.entity"} )
@ComponentScan(basePackages= {"com.drkrishi.usermanagement.jwtsecurity.security", 
		"com.drkrishi.usermanagement.Usermanagement", "com.drkrishi.usermanagement.jwtsecurity.config", "com.drkrishi.usermanagement.properties"})
@EnableJpaRepositories("com.drkrishi.usermanagement.dao.repository")
@EnableSwagger2
//@EnableEurekaClient
public class UsermanagementApplication{
	private static final Logger LOGGER = LogManager.getLogger(UsermanagementApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(UsermanagementApplication.class, args);		
		LOGGER.info("INFO: Server started successfully");		
	}
}