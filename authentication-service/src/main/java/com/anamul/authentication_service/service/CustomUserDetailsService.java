package com.anamul.authentication_service.service;

import com.anamul.authentication_service.entity.UserEntity;
import com.anamul.authentication_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = new BCryptPasswordEncoder().encode("admin123");

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if(ADMIN_EMAIL.equals(email)){
            return User.withUsername(ADMIN_EMAIL)
                    .password(ADMIN_PASSWORD)
                    .roles("ADMIN") // ROLE_ADMIN
                    .build();
        }
        UserEntity userEntity=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found Exception"));

        return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole())));//Collections.emptyList());

    }
}
