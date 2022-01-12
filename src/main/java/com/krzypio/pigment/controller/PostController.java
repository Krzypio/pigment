package com.krzypio.pigment.controller;

import com.krzypio.pigment.entity.Post;
import com.krzypio.pigment.entity.Treatment;
import com.krzypio.pigment.entity.User;
import com.krzypio.pigment.exception.other.PostNotFoundException;
import com.krzypio.pigment.exception.other.TreatmentNotFoundException;
import com.krzypio.pigment.exception.user.UserAlreadyExistException;
import com.krzypio.pigment.exception.user.UserNotFoundException;
import com.krzypio.pigment.repository.PostRepository;
import com.krzypio.pigment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/posts")
    public List<Post> retrieveAllPosts(){
        return postRepository.findAll();
    }

    @GetMapping("/posts/{id}")
    public Post retrieveUserById(@PathVariable long id){
        Optional<Post> retrievedPost = postRepository.findById(id);
        retrievedPost.orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found.")); //to implement
        return retrievedPost.get();
    }

    @PostMapping("/posts")
    public ResponseEntity createPost(@Valid @RequestBody Post post){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> retrievedUser = userRepository.findByUsername(authentication.getName());
        retrievedUser.orElseThrow(() -> new UserNotFoundException("During create Post myUser not found."));

        Post newPost = new Post(post.getDescription(), retrievedUser.get());
        Post savedPost = postRepository.save(newPost);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedPost.getId())
                .toUri();

        return  ResponseEntity.created(location).body(savedPost);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity deleteById(@PathVariable long id){
        Optional<Post> existingPost = postRepository.findById(id);
        existingPost.orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found."));
        postRepository.deleteById(id);
        return ResponseEntity.ok("Post with id " + id + " deleted.");
    }


}
