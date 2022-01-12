package com.krzypio.pigment.repository;

import com.krzypio.pigment.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
