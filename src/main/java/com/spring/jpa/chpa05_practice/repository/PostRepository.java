package com.spring.jpa.chpa05_practice.repository;

import com.spring.jpa.chpa05_practice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Long> {

}
