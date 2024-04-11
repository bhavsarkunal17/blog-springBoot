package com.project.blog.services.Impl;

import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.model.Comment;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.payloads.CommentDto;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.UserDto;
import com.project.blog.repositories.CommentRepo;
import com.project.blog.repositories.PostRepo;
import com.project.blog.repositories.UserRepo;
import com.project.blog.services.CommentService;
import com.project.blog.services.PostService;
import com.project.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final ModelMapper modelMapper;

    private final UserRepo userRepo;
    private final PostRepo postRepo;


    @Autowired
    public CommentServiceImpl(CommentRepo commentRepo, ModelMapper modelMapper, UserRepo userRepo, PostRepo postRepo) {
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, int userId, int postId) {

        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        comment.setCommentedBy(user);
        comment.setInPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public CommentDto getCommentById(int commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Id",commentId));
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllCommentsByPost(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        List<Comment> commentList = this.commentRepo.getAllByInPost(post);
        return commentList.stream().map(comment -> this.modelMapper.map(comment,CommentDto.class)).toList();
    }

    @Override
    public List<CommentDto> getAllCommentsByUser(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        List<Comment> commentList = this.commentRepo.getAllByCommentedBy(user);
        return commentList.stream().map(comment -> this.modelMapper.map(comment,CommentDto.class)).toList();
    }

    @Override
    public List<CommentDto> getAllCommentsByUserAndByPost(int userId, int postId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        List<Comment> commentList = this.commentRepo.getAllByInPostAndCommentedBy(post,user);
        return commentList.stream().map(comment -> this.modelMapper.map(comment,CommentDto.class)).toList();
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, int commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Id",commentId));
        comment.setMessage(commentDto.getMessage());
        Comment updatedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(updatedComment,CommentDto.class);
    }

    @Override
    public void deleteCommentById(int commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Id",commentId));
        this.commentRepo.delete(comment);

    }
}
