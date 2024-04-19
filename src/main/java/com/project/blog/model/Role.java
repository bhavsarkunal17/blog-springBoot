package com.project.blog.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Entity
@Table(name =  "roles")
@Getter
@Setter
public class Role {


    @Id
    private int id;

    private String name;
}
