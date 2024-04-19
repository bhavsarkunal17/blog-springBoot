package com.project.blog.payloads;


import com.project.blog.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {


    private int id;
    @NotEmpty
    private String name;
    @Email
    private String email;
    @NotEmpty
    @Size(min = 2, max =10)
    private String password;
    private String about;
    private RoleDto roleDto;
    private List<Role> roles = new ArrayList<>();
}
