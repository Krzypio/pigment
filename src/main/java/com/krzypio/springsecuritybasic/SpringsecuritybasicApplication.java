package com.krzypio.springsecuritybasic;

import com.krzypio.security.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScans({
		@ComponentScan("com.krzypio.controller"),
		@ComponentScan("com.krzypio.security")
})
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EntityScan(basePackages = "com.krzypio.security.models")
public class SpringsecuritybasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecuritybasicApplication.class, args);
	}

}
