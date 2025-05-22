package com.project.flightmanagementsystem.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.project.flightmanagementsystem.entity.User;
import com.project.flightmanagementsystem.exception.UserNotFoundException;
import com.project.flightmanagementsystem.repository.IUserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
    private  IUserRepository userPsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        Optional<User> opt=userPsRepository.findByUsername(username);
        if(opt.isEmpty()){
            throw new UserNotFoundException("user not found");
        }
        User ps=opt.get();
        
        
        org.springframework.security.core.userdetails.User user=new org.springframework.security.core.userdetails.User(ps.getUsername(),
        		ps.getPassword(),
        		ps.getRoles().stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
        return user;
        		
        		
    }
}
