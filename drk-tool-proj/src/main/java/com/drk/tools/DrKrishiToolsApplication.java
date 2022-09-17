package com.drk.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@SpringBootApplication
public class DrKrishiToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrKrishiToolsApplication.class, args);
	}

}
