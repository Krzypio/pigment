package com.krzypio.security;

import com.krzypio.security.models.User;
import com.krzypio.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryCommandLineRunner.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("user", passwordEncoder.encode("pass"), "ROLE_USER");
        User savedUser = userRepository.save(user);
        log.info("New User is created: " + savedUser);
    }
}
