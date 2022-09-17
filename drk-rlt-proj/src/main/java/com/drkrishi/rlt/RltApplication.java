package com.drkrishi.rlt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EntityScan( "com.drkrishi.rlt.entity" )
@EnableJpaRepositories( "com.drkrishi.rlt.repository")
public class RltApplication {

	public static void main(String[] args) {
		SpringApplication.run(RltApplication.class, args);
	}

}
