package com.onuroztas.simpleapi.service;

import com.onuroztas.simpleapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check database users first
        return userRepository.findByUsername(username)
            .map(user -> (UserDetails) user)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
