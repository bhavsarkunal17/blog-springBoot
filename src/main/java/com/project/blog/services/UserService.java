package com.project.blog.services;

import com.project.blog.model.User;
import com.project.blog.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);
    UserDto registerUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, int id);
    UserDto getUserById(int id);
    List<UserDto> getAllUsers();
    void deleteUser(int id);



}
