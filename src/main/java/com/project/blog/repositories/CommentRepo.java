package com.project.blog.repositories;

import com.project.blog.model.Comment;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

    List<Comment> getAllByCommentedBy(User user);
    List<Comment> getAllByInPost(Post post);
    List<Comment> getAllByInPostAndCommentedBy(Post inPost, User commentedBy);
}
