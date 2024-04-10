package com.project.blog.services.Impl;

import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.model.Category;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.payloads.PostDto;
import com.project.blog.repositories.CategoryRepo;
import com.project.blog.repositories.PostRepo;
import com.project.blog.repositories.UserRepo;
import com.project.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto, int userId, int categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImage("defaultImg.png");
        post.setCreationDate(new Date());
        post.setCreatedBy(user);
        post.setCategory(category);
        Post createdPost = this.postRepo.save(post);
        return this.modelMapper.map(createdPost,PostDto.class);
    }

    @Override
    public PostDto getPostById(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = this.postRepo.findAll();
        List<PostDto> postDtoList = postList.stream().map(post -> this.modelMapper.map(post,PostDto.class)).toList();
        return postDtoList;
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        post.setPostTitle(postDto.getPostTitle());
        post.setContent(postDto.getContent());
        post.setImage(postDto.getImage());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);

    }

    @Override
    public void deletePost(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getPostByUserId(int userId) {
        User user =  this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        List<Post> postList = this.postRepo.findAllByCreatedBy(user);
        return postList.stream().map(post -> this.modelMapper.map(post,PostDto.class)).toList();
    }

    @Override
    public List<PostDto> getPostByCategoryId(int categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        List<Post> postList = this.postRepo.findAllByCategory(category);
        return postList.stream().map(post -> this.modelMapper.map(post,PostDto.class)).toList();
    }
}
