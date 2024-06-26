package com.project.blog.exceptions;

import com.project.blog.payloads.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiResponse>resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String,String >>methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String field = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(field,message);
        });

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ApiResponse>illegalArgumentException(IllegalArgumentException ex){

        ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ApiResponse>badCredentialException(BadCredentialsException ex){

        ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(ApiException.class)
//    ResponseEntity<ApiResponse>apiException(ApiException ex){
//
//        ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
//        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
//    }


}
