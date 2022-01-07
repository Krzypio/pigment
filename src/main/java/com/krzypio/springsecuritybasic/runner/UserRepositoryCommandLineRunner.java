package com.krzypio.springsecuritybasic.runner;

import com.krzypio.springsecuritybasic.entity.User;
import com.krzypio.springsecuritybasic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserRepositoryCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryCommandLineRunner.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User("admin", passwordEncoder.encode("pass"), "ROLE_ADMIN");
        User adam = new User("adam", passwordEncoder.encode("pass"), "ROLE_USER");
        User ewa = new User("ewa", passwordEncoder.encode("pass"), "ROLE_USER");
        List<User> users = userRepository.saveAll(Arrays.asList(admin, adam, ewa));
        log.info("New Users are created: " + users);
    }
}
