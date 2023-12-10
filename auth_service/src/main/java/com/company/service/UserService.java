package com.company.service;

import com.company.client.TraineeClient;
import com.company.client.TrainerClient;
import com.company.config.security.JwtService;
import com.company.domain.User;
import com.company.dto.*;
import com.company.repository.UserRepository;
import com.company.util.Util;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TrainerClient trainerClient;
    private final TraineeClient traineeClient;

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

    public List<User> findListByUsername(String username) {
        return userRepository.findListByUsername(username);
    }

    public Boolean validateToken(String token) {
        return jwtService.isValidToken(token);
    }

    public TrainerDTO registerTrainer(TrainerRegisterDTO dto) {
        var username = generateUserName(dto.firstName(), dto.lastName());
        var password = Util.generatePassword();
        var user = User.builder()
                .active(true)
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        user = userRepository.save(user);
        TrainerDTO trainerDTO = trainerClient.create(new TrainerCreateDTO(username, user.getPassword(), user.isActive(), user.getFirstName(), user.getLastName(), dto.specializationId()));
        return new TrainerDTO(trainerDTO.id(), trainerDTO.username(), password, trainerDTO.firstName(), trainerDTO.lastName(), trainerDTO.specializationId(), trainerDTO.active());
    }


    public TraineeDTO registerTrainee(TraineeRegisterDTO dto) {
        var username = generateUserName(dto.firstName(), dto.lastName());
        var password = Util.generatePassword();
        var user = User.builder()
                .active(true)
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        user = userRepository.save(user);
        TraineeDTO traineeDTO = traineeClient.create(new TraineeCreateDTO(username, user.getPassword(), user.isActive(), user.getFirstName(), user.getLastName(), dto.birthDate(), dto.address()));
        return new TraineeDTO(traineeDTO.id(), traineeDTO.username(), password, traineeDTO.firstName(), traineeDTO.lastName(), traineeDTO.active(), traineeDTO.birthDate(), traineeDTO.address());
    }

    private String generateUserName(String firstName, String lastName) {
        String username = firstName + "." + lastName;
        List<User> listByUsername = findListByUsername(username);
        if (!listByUsername.isEmpty()) {
            username = username + "-" + listByUsername.size();
        }
        return username;
    }

    public void updatePassword(UpdatePasswordDTO dto) {
        User byUsername = findByUsername(dto.username());
        byUsername.setPassword(dto.newPassword());
        userRepository.save(byUsername);
        SecurityContextHolder.clearContext();
    }
}
