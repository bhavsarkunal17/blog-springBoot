package com.project.blog.model;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_name", nullable = false,length = 100)
    private String name;
    private String email;
    private String password;
    private String about;
    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();
    @OneToMany(mappedBy = "commentedBy",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> commentList;

   
}
