package com.quat.Kumquat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.quat.Kumquat.model" })
@EnableJpaRepositories(basePackages = {"com.quat.Kumquat.repository"})
public class KumquatApplication {

	public static void main(String[] args) {
		SpringApplication.run(KumquatApplication.class, args);
	}
	

}
