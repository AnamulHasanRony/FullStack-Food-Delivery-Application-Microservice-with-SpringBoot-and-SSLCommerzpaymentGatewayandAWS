package com.anamul.authentication_service.filter;

import com.anamul.authentication_service.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("REQUEST URI: " + request.getRequestURI());

//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        if (request.getRequestURI().contains("/api/payment/")) {
            filterChain.doFilter(request, response);
            return;
        }



   final String authHeader=request.getHeader("Authorization");
   if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
       String token= authHeader.substring(7);
       String email=jwtUtil.extractUsername(token);
       String role = jwtUtil.extractRole(token);

       if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           UserDetails userDetails=userDetailsService.loadUserByUsername(email);
           if(jwtUtil.isTokenValid(token,userDetails)){
               UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,
                       null, Collections.singletonList(new SimpleGrantedAuthority(role)));  ///userDetails.getAuthorities()
               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);

           }

       }

   }

        filterChain.doFilter(request,response);




    }
}
