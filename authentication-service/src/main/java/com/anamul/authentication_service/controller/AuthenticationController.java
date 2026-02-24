package com.anamul.authentication_service.controller;

import com.anamul.authentication_service.io.AuthenticationRequest;
import com.anamul.authentication_service.io.AuthenticationResponse;
import com.anamul.authentication_service.service.CustomUserDetailsService;
import com.anamul.authentication_service.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        final String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();///
        final String jwtToken = jwtUtil.generateToken(userDetails,role);

        return new AuthenticationResponse(authenticationRequest.getEmail(), jwtToken, role);
    }

}
