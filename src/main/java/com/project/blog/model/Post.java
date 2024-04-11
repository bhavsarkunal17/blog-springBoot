package com.project.blog.model;

import com.project.blog.payloads.CategoryDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    @Column(name = "title", nullable = false)
    private String postTitle;
    @Column(name = "content", nullable = false,length = 10000)
    private String content;
    private Date creationDate;
    private String image;
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "inPost",cascade = CascadeType.ALL)
    private List<Comment> comments;
}
