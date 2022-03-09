package com.akkanben.codefellowship.repositories;

import com.akkanben.codefellowship.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
