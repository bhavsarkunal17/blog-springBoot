package com.project.blog.controllers;


import com.project.blog.model.Post;
import com.project.blog.payloads.PostDto;
import com.project.blog.services.FileService;
import com.project.blog.services.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("api/file/")
public class FileServiceController {

    private final FileService fileService;
    private final PostService postService;
    @Value("${project.folderPath}")
    private String folderPath;

    @Autowired
    public FileServiceController(FileService fileService, PostService postService) {
        this.fileService = fileService;
        this.postService = postService;
    }

    @PostMapping("/upload")
    ResponseEntity<PostDto>uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("postId") int postId) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);
        String  uploadedFile = this.fileService.uploadFile(folderPath,file);
        postDto.setImage(uploadedFile);
        PostDto postDtoUpdated = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDtoUpdated, HttpStatus.CREATED);

    }
    @GetMapping(value = "/download", produces = MediaType.IMAGE_PNG_VALUE)
    void downloadFIle(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        InputStream fileStream = this.fileService.getFile(folderPath,fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(fileStream, response.getOutputStream());



    }
}
