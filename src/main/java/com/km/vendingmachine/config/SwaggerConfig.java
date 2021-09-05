package com.km.vendingmachine.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
		private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
				Arrays.asList("application/json","application/xml"));
	 	@Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()                                  
	          .apis(RequestHandlerSelectors.basePackage("com.km.vendingmachine"))              
	          .paths(PathSelectors.regex("/.*"))                          
	          .build()
	          .produces(DEFAULT_PRODUCES_AND_CONSUMES)
	          .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
	          .apiInfo(apiDetails());                                           
	    }
	 	
	 	private ApiInfo apiDetails() {
	 		return new ApiInfo(
	 				"Vending Machine API",
	 				"Spring Boot REST API for Vending Machine",
	 				"1.0",
	 				"",
	 				new springfox.documentation.service.Contact("Kenneth Macaraeg", "https://www.linkedin.com/in/kenneth-macaraeg-37967729/", "jbourne.kenmacpro17@gmail.com"),
	 				"API License",
	 				"https://www.linkedin.com/in/kenneth-macaraeg-37967729/",
	 				Collections.emptyList()
	 				);
	 	}
}
