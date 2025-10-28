package com.food.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.food.server.models.User;
import com.food.server.repository.UserRepository;

@Service
public class AuthService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public User signup(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public String login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!encoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        return jwtService.generateToken(email);
    }
    public User getUserFromToken(String token){
        String email = jwtService.extractEmail(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
