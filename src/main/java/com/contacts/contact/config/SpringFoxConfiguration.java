/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contacts.contact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author dell
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfiguration extends WebMvcConfigurationSupport {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.redis.Redis.controller")).build()
				.apiInfo(metaData());

	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Spring Boot Redis Cache")
				.description("\"Spring Boot REST API for Spring Boot Redis Cache\"")
				.version("0.0.1")
				//.license("Apache License Version 2.0")
				//.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.contact("Name: Musa Dabra "+" Github: https://github.com/musadabra"+" Email: musadabra@gmail.com")
				.build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}