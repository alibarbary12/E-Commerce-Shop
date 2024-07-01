package com.e_commerce.inventory_service.service;


import com.e_commerce.inventory_service.entity.MyUser;
import com.e_commerce.inventory_service.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MyUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            var userObj=user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .authorities(userObj.getRoles().stream()  // Assuming getRoles() returns a Set<String> of roles
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Prefix each role with "ROLE_"
                            .collect(Collectors.toList()))
                    .build();


        }
        else{
            throw new UsernameNotFoundException(username);
        }
    }
    public MyUser loadMyUserByUsername(String username) throws UsernameNotFoundException{
        Optional<MyUser> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            var userObj=user.get();
            return userObj;
        }
        else{
            throw new UsernameNotFoundException(username);
        }


    }

    public String getCurrentUsername(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }
}
