package com.project.blog.model;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    private  String message;
    @ManyToOne
    @JoinColumn(name="userId")
    private User commentedBy;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post inPost;


}
