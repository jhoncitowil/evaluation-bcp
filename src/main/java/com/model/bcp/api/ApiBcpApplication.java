package com.model.bcp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("com.model.bcp.api.repository")
@EntityScan("com.model.bcp.domain")
@ComponentScan("com.model.bcp")
@SpringBootApplication()
public class ApiBcpApplication {

  private static final Logger log = LoggerFactory.getLogger(ApiBcpApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApiBcpApplication.class, args);
	}

	
}
