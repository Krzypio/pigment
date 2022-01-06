package com.krzypio.security;

import com.krzypio.security.entity.User;
import com.krzypio.security.service.UserDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

@EntityScan(basePackages = "com.krzypio.security")
@Component
public class UserDaoServiceCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserDaoServiceCommandLineRunner.class);

    @Autowired
    private UserDAOService userDAOService;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("user", "pass", "ROLE_USER");
        long insert = userDAOService.insert(user);
        log.info("New User is created: " + user);
    }
}
