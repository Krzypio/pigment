package com.krzypio.springsecuritybasic;

import com.krzypio.springsecuritybasic.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//I is needed if some components and entities are outside main package (springsecuritybasic)
//@ComponentScans({
//		@ComponentScan("com.krzypio.springsecuritybasic.controller"),
//		@ComponentScan("com.krzypio.springsecuritybasic.security")
//})
//@EntityScan(basePackages = "com.krzypio.springsecuritybasic.security")
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SpringsecuritybasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecuritybasicApplication.class, args);
	}

}
