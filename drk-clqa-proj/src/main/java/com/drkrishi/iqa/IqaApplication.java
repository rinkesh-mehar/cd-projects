package com.drkrishi.iqa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
//@EnableEurekaClient
@EntityScan(value="com.drkrishi.iqa.entity")
@SpringBootApplication
public class IqaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IqaApplication.class, args);
	}
	
}
