package com.project.blog.payloads;

import com.project.blog.model.Post;
import com.project.blog.model.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CommentDto {


    private int commentId;
    @NotEmpty
    private  String message;
    private UserDto commentedBy;
}
