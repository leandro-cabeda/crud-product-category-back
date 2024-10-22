package com.crud_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@SpringBootApplication(scanBasePackages = {"com.crud_service.helper"})
@EnableAutoConfiguration
@ComponentScan
public class CrudServiceApplication {
	private static final Logger log = LoggerFactory.getLogger(CrudServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		restTemplate.getInterceptors().add((request, body, execution) -> {
			ClientHttpResponse response = execution.execute(request, body);
			log.info("Response Body: {}", StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
			return response;
		});
		return restTemplate;
	}
}
