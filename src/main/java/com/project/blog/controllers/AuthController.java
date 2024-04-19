package com.project.blog.controllers;


import com.project.blog.exceptions.ApiException;
import com.project.blog.payloads.JwtAuthRequest;
import com.project.blog.payloads.UserDto;
import com.project.blog.security.JwtAuthenticationResponse;
import com.project.blog.security.JwtTokenHelper;
import com.project.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private JwtTokenHelper jwtTokenHelper;
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private UserService userService;


    @Autowired
    public AuthController(JwtTokenHelper jwtTokenHelper, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse>createToken(@RequestBody JwtAuthRequest request) throws Exception {
        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(this.jwtTokenHelper.generateToken(userDetails));
        return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.CREATED);
    }

    public void authenticate(String username, String password) throws Exception {

        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(username,password);
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);


        }catch (BadCredentialsException ex){

            throw new BadCredentialsException("Invalid username or password!!");

        }
    }

    @PostMapping("register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto){
        UserDto registerdUser = userService.registerUser(userDto);
        return new ResponseEntity<>(registerdUser,HttpStatus.CREATED);
    }
}
