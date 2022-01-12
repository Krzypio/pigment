package com.krzypio.pigment.runner;

import com.krzypio.pigment.entity.Post;
import com.krzypio.pigment.entity.User;
import com.krzypio.pigment.repository.PostRepository;
import com.krzypio.pigment.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserPostRepositoryCLR implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserPostRepositoryCLR.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User("admin", passwordEncoder.encode("pass"), "ROLE_ADMIN");
        User adam = new User("worker", passwordEncoder.encode("pass"), "ROLE_WORKER");
        User ewa = new User("wet", passwordEncoder.encode("pass"), "ROLE_WET");
        List<User> addedUsers = userRepository.saveAll(Arrays.asList(admin, adam, ewa));
        log.info("New Users are created: " + addedUsers);

        Post p1 = new Post("Hello from admin", admin);
        Post p2 = new Post("Goodbye from admin", admin);
        Post p3 = new Post("Hello from adam", admin);

        List<Post> addedPosts = postRepository.saveAll(Arrays.asList(p1,p2,p3));
        log.info("New Posts are created: " + addedPosts);
    }
}
