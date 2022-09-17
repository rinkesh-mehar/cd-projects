package com.drkrishi.usermanagement.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.drkrishi.usermanagement"))
                .paths(regex("/*.*"))
                .build().apiInfo(apiEndPointsInfo());
    }
	
	 private ApiInfo apiEndPointsInfo() {
	        return new ApiInfoBuilder().title("Spring Boot REST API")
	            .description("Public Relationship Manager(PRM) REST API")
	            .contact(new Contact("3I-Infotech", "www.3iinfotech.com", "admin@3i-infotech.com"))
				/*
				 * .license("Apache 2.0")
				 * .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				 */
	            .version("1.0.0")
	            .build();
	    }
}
