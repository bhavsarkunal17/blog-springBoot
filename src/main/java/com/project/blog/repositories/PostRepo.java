package com.project.blog.repositories;

import com.project.blog.model.Category;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findAllByCategory(Category category);
    List<Post> findAllByCreatedBy(User user);
    List<Post> findByPostTitleContainingIgnoreCase(String content);
}
