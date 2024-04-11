package com.project.blog.services;

import com.project.blog.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,int userId, int postId);
    CommentDto getCommentById(int commentId);
    List<CommentDto> getAllCommentsByPost(int postId);
    List<CommentDto> getAllCommentsByUser(int userId);

    List<CommentDto> getAllCommentsByUserAndByPost(int userId, int postId);
    CommentDto updateComment(CommentDto commentDto, int commentId);
    void deleteCommentById(int commentId);
}
