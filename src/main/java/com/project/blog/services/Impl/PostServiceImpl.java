package com.project.blog.services.Impl;

import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.model.Category;
import com.project.blog.model.Post;
import com.project.blog.model.User;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;
import com.project.blog.repositories.CategoryRepo;
import com.project.blog.repositories.PostRepo;
import com.project.blog.repositories.UserRepo;
import com.project.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepo postRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepo postRepo, CategoryRepo categoryRepo, UserRepo userRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortField, String sortType) {
        Sort sort = sortType.equalsIgnoreCase("asc")?Sort.by(sortField).ascending():Sort.by(sortField).descending();

        PageRequest pageRequest = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> postPage = this.postRepo.findAll(pageRequest);
        List<PostDto> postDtoList = postPage.getContent().stream().map(post -> this.modelMapper.map(post,PostDto.class)).toList();
        return generatePostRespone(postDtoList,postPage);
    }

    private PostResponse generatePostRespone(List<PostDto> postDtoList, Page<Post> postPage) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
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
