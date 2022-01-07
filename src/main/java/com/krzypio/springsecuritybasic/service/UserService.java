package com.krzypio.springsecuritybasic.service;

import com.krzypio.springsecuritybasic.entity.User;
import com.krzypio.springsecuritybasic.exception.user.UserPasswordNotMatchException;
import com.krzypio.springsecuritybasic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(){
    }

    public User save(User user){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        User savedUser = new User(user.getUsername(), hashedPassword, user.getRoles());
        savedUser = userRepository.save(savedUser);
        return savedUser;
    }

    public User updatePassword(String oldPassword, String newPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        userOpt.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        User user = userOpt.get();
        boolean passwordsMatches = passwordEncoder.matches(oldPassword, user.getPassword());

        if (passwordsMatches) {
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);
            user = userRepository.save(user);
        } else
            throw new UserPasswordNotMatchException("OldPassword from input doesn't match with database");
        return user;
    }
}
