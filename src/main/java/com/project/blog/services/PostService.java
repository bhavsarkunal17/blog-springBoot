package com.project.blog.services;

import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,int userId, int categoryId);
    PostDto getPostById(int postId);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortField, String sortType);
    PostDto updatePost(PostDto postDto,int postId);
    void deletePost(int postId);

    List<PostDto> getPostByUserId(int userId);
    List<PostDto> getPostByCategoryId(int categoryId);
    List<PostDto> getPostBySearch(String keywords);

}
