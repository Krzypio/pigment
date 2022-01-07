package com.krzypio.pigment;

import com.krzypio.pigment.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//I is needed if some components and entities are outside main package (pigment)
//@ComponentScans({
//		@ComponentScan("com.krzypio.pigment.controller"),
//		@ComponentScan("com.krzypio.pigment.security")
//})
//@EntityScan(basePackages = "com.krzypio.pigment.security")
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class PigmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigmentApplication.class, args);
	}

}
