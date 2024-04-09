package com.project.blog.services.Impl;

import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.model.User;
import com.project.blog.payloads.UserDto;
import com.project.blog.repositories.UserRepo;
import com.project.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepo userRepo;
    @Override
    public UserDto addUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int id) {

        User originalUser = this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
        originalUser.setName(userDto.getName());
        originalUser.setAbout(userDto.getAbout());
        originalUser.setEmail(userDto.getEmail());
        originalUser.setPassword(userDto.getPassword());
        User savedUser = this.userRepo.save(originalUser);
        return userToUserDto(savedUser);

    }

    @Override
    public UserDto getUserById(int id) {
        User user = this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
        return userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users =  this.userRepo.findAll();
       List<UserDto> userDtos =  users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
       return userDtos;
    }

    @Override
    public void deleteUser(int id) {
            User user = this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
            this.userRepo.delete(user);
    }

    private User userDtoToUser(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setAbout(userDto.getAbout());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
    private UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setAbout(user.getAbout());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
