package com.anamul.authentication_service.config;

import com.anamul.authentication_service.filter.JwtFilter;
import com.anamul.authentication_service.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;

    @Value("${frontend.base.url:http://localhost:5317}")
    private String frontendBaseUrl;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtFilter jwtFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
//,
        httpSecurity.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/payment/**", "/api/ping").permitAll()
                        .requestMatchers("/api/register", "/api/login","/api/foods/**","/api/contactUs").permitAll()
                        .requestMatchers("/api/order/all", "/api/order/update/**").hasRole("ADMIN").anyRequest().authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CorsFilter corsFilter(CorsConfigurationSource corsConfigurationSource){
//        return new CorsFilter(corsConfigurationSource());
//    }
/// ,

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config=new CorsConfiguration();//
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5177","https://sandbox.sslcommerz.com", "http://localhost:80", frontendBaseUrl));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource= new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config);


        CorsConfiguration paymentConfig = new CorsConfiguration();
//        paymentConfig.setAllowedOrigins(List.of("*"));
        paymentConfig.setAllowedOriginPatterns(List.of("*"));
        paymentConfig.setAllowedMethods(List.of("POST","GET"));
        paymentConfig.setAllowedHeaders(List.of("*"));
        paymentConfig.setAllowCredentials(false);
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/api/payment/**", paymentConfig);
        return urlBasedCorsConfigurationSource;



    }
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);

    }

}
