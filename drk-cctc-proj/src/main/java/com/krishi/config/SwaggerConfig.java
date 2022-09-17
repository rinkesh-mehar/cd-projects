package com.krishi.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.krishi"))
                .paths(regex("/*.*"))
                .build().apiInfo(apiEndPointsInfo());
    }
	
	 private ApiInfo apiEndPointsInfo() {
	        return new ApiInfoBuilder().title("Spring Boot REST API")
	            .description("Call Center Tele-Caller(CCTC) REST API")
	            .contact(new Contact("3I-Infotech", "www.3iinfotech.com", "admin@3i-infotech.com"))
	            .version("1.0.0")
	            .build();
	    }
}
