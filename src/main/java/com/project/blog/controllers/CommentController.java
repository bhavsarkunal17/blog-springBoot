package com.project.blog.controllers;


import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.CommentDto;
import com.project.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/upload")
    ResponseEntity<CommentDto> uploadComment(@RequestBody CommentDto commentDto,
                                             @RequestParam("userId") int userId, @RequestParam("postId") int postId){

        CommentDto uploadedCommentDto = this.commentService.createComment(commentDto,userId,postId);
        return new ResponseEntity<>(uploadedCommentDto, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    ResponseEntity<List<CommentDto>> getComments(@RequestParam(value = "userId",required = false) Integer userId,
                                                 @RequestParam(value = "postId",required = false) Integer postId){
        List<CommentDto> commentDtos = null;
        if (userId == null && postId == null) {
            throw new IllegalArgumentException("At least one of userId or postId must be provided");
        }else if(userId != null && postId != null){
           commentDtos =  this.commentService.getAllCommentsByUserAndByPost(userId,postId);
        }else if(userId != null){
            commentDtos = this.commentService.getAllCommentsByUser(userId);
        }else{
            commentDtos = this.commentService.getAllCommentsByPost(postId);
        }
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);

    }

    @GetMapping("get/{id}")
    ResponseEntity<CommentDto> getCommentById(@PathVariable("id") int commentId){
        CommentDto commentDto = this.commentService.getCommentById(commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    ResponseEntity<CommentDto> updateById(@RequestBody CommentDto commentDto,@PathVariable("id") int commentId){
        CommentDto updatedCommentDto = this.commentService.updateComment(commentDto,commentId);
        return new ResponseEntity<>(updatedCommentDto,HttpStatus.OK);
    }
    @DeleteMapping("delete/{id}")
    ResponseEntity<ApiResponse> deleteById(@PathVariable("id") int commentId){
        this.commentService.deleteCommentById(commentId);
        return new ResponseEntity<>(new ApiResponse("Commnet is deleted",true),HttpStatus.OK);
    }
}
