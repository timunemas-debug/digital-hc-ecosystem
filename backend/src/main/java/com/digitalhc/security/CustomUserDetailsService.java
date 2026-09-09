package com.digitalhc.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalhc.model.User;
import com.digitalhc.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);

        if (user.isPresent()) {
            return new CustomUserDetails(user.get());
        }

        throw new UsernameNotFoundException("Email tidak ditemukan!");
    }
}
