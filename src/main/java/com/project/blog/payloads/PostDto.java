package com.project.blog.payloads;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private int postId;
    @NotEmpty
    private String postTitle;
    @NotEmpty
    private String content;
    private Date creationDate;
    private String image;
    private UserDto createdBy;
    private CategoryDto category;
}
