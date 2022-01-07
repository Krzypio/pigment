package com.krzypio.pigment.service;

import com.krzypio.pigment.entity.User;
import com.krzypio.pigment.exception.user.UserPasswordNotMatchException;
import com.krzypio.pigment.exception.user.UserPasswordNotValidException;
import com.krzypio.pigment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

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

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {

            isNewPasswordValid(newPassword);

            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);
            user = userRepository.save(user);
        } else
            throw new UserPasswordNotMatchException("OldPassword from input doesn't match with database");
        return user;
    }

    private boolean isNewPasswordValid(String newPassword){
        //check if new password is valid with User Entity. It sot clean.
        String testUsername = "_test";
        Optional<User> test = userRepository.findByUsername(testUsername);
        if(test.isPresent())
            userRepository.deleteById(test.get().getId());
        User testUser = new User(testUsername, newPassword, "ROLE_USER");

        try {
            userRepository.save(testUser);
            userRepository.delete(testUser);
        } catch (TransactionSystemException e) {
            Throwable mostSpecificCause = e.getMostSpecificCause();
            throw new UserPasswordNotValidException(mostSpecificCause.getMessage());
        }
        return  true;
    }
}
