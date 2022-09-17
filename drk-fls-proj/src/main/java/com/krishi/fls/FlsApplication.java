package com.krishi.fls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EntityScan(value="com.krishi.fls.entity")
@EnableSwagger2
//@EnableEurekaClient
public class FlsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlsApplication.class, args);
	}

}
