package com.anamul.authentication_service.controller;

import com.anamul.authentication_service.io.RegisterRequest;
import com.anamul.authentication_service.io.RegisterResponse;
import com.anamul.authentication_service.service.UserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserServiceImplementation userServiceImplementation;

    public UserController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest){
        return userServiceImplementation.registerUser(registerRequest);

    }
}
