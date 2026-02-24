package com.anamul.authentication_service.service;

import com.anamul.authentication_service.io.RegisterRequest;
import com.anamul.authentication_service.io.RegisterResponse;

public interface UserService {
    RegisterResponse registerUser(RegisterRequest registerRequest);
    String findUserId();
    String findUserEmail();


}
