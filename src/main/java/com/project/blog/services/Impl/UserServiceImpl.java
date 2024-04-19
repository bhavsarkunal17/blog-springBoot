package com.project.blog.services.Impl;

import com.project.blog.config.Constants;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.model.Role;
import com.project.blog.model.User;
import com.project.blog.payloads.UserDto;
import com.project.blog.repositories.RoleRepo;
import com.project.blog.repositories.UserRepo;
import com.project.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {



    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto registerUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto,User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role roleUser = this.roleRepo.findById(Constants.USER_ID).get();
        user.getRoles().add(roleUser);
        User savesUser = this.userRepo.save(user);
        return  this.modelMapper.map(savesUser,UserDto.class);


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
       List<UserDto> userDtos =  users.stream().map(user->this.userToUserDto(user)).toList();
       return userDtos;
    }

    @Override
    public void deleteUser(int id) {
            User user = this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
            this.userRepo.delete(user);
    }

    private User userDtoToUser(UserDto userDto){
        return modelMapper.map(userDto,User.class);
    }
    private UserDto userToUserDto(User user){

        return modelMapper.map(user,UserDto.class);
    }
}
