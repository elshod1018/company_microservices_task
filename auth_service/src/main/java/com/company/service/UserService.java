package com.company.service;

import com.company.config.security.JwtService;
import com.company.domain.User;
import com.company.dto.UserByUsername;
import com.company.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String generateToken(@NonNull UserByUsername dto) {
        String username = dto.username();
        String password = dto.password();
        User byUsername = findByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
        return jwtService.generateAccessToken(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Boolean validateToken(String token) {
        return jwtService.isValidToken(token);
    }
}
