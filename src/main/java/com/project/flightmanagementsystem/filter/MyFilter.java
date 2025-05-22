package com.project.flightmanagementsystem.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.flightmanagementsystem.jwt.JwtUtil;
import com.project.flightmanagementsystem.service.MyUserDetailsService;

import java.io.IOException;
@Component

public class MyFilter extends OncePerRequestFilter {
	
	@Autowired
    private JwtUtil jwtUtil;
	@Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token=request.getHeader("Authorization");
        if(token!=null){
          var username=  jwtUtil.getSubject(token);
            System.out.println(username);
          if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
              var userDetails=userDetailsService.loadUserByUsername(username);
                boolean isTokenValid=jwtUtil.validateToken(token,userDetails.getUsername());
                if(isTokenValid){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new
                            UsernamePasswordAuthenticationToken(userDetails.getUsername()
                            ,userDetails.getPassword()
                            ,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }
          }
        }

    filterChain.doFilter(request, response);

    }
}

