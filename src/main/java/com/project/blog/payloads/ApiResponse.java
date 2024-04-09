package com.project.blog.payloads;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {

    String message;
    boolean success;

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.success = status;
    }
}
