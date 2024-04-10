package com.project.blog.services;

import com.project.blog.payloads.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,int userId, int categoryId);
    PostDto getPostById(int postId);
    List<PostDto> getAllPosts();
    PostDto updatePost(PostDto postDto,int postId);
    void deletePost(int postId);

    List<PostDto> getPostByUserId(int userId);
    List<PostDto> getPostByCategoryId(int categoryId);

}
