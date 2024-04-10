package com.project.blog.controllers;

import com.project.blog.config.Constants;
import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;
import com.project.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/create")
    ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestParam("userId") int userId, @RequestParam("categoryId") int categoryId){

        PostDto createdPost = this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                             @RequestParam(value = "pageSize",defaultValue = Constants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                             @RequestParam(value = "sortField",defaultValue = Constants.DEFAULT_SORT_FIELD,required = false) String sortField,
                                             @RequestParam(value = "sortType",defaultValue = Constants.DEFAULT_SORT_TYPE,required = false) String sortType){

        PostResponse postResponse = this.postService.getAllPosts(pageNo,pageSize,sortField,sortType);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable("id") int postId){
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    @GetMapping("/getByUser")
    ResponseEntity<List<PostDto>> getPostByUserId(@RequestParam("userId") int userId){
        List<PostDto> postDtoList = this.postService.getPostByUserId(userId);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }
    @GetMapping("/getByCategory")
    ResponseEntity<List<PostDto>> getPostByCategoryId(@RequestParam("categoryId") int categoryId){
        List<PostDto> postDtoList = this.postService.getPostByCategoryId(categoryId);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    ResponseEntity<List<PostDto>> getPostBySearch(@PathVariable("keywords") String keywords){
        List<PostDto> postDtoList = this.postService.getPostBySearch(keywords);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") int postId){
        PostDto updatedPostDto = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPostDto,HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity<ApiResponse> deletePostByID(@PathVariable("id") int postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post is deleted successfully",true),HttpStatus.OK);
    }
}
