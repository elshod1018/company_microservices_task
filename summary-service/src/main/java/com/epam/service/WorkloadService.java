package com.epam.service;

import com.epam.client.MainServiceClient;
import com.epam.dto.TrainerWorkloadDTO;
import com.epam.dto.TrainerWorkloadUpdateDTO;
import com.epam.entity.TrainerWorkload;
import com.epam.repository.TrainerWorkloadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkloadService {
    private final TrainerWorkloadRepository trainerWorkloadRepository;
    private final MainServiceClient mainServiceClient;

    @Modifying
    @Transactional
    public TrainerWorkload workload(TrainerWorkloadDTO dto) {
        Boolean aBoolean = mainServiceClient.existByUsername(dto.username());
        if (!aBoolean) {
            throw new RuntimeException("User not found with username '%s'".formatted(dto.username()));
        }
        if ("ADD".equalsIgnoreCase(dto.actionType())) {
            TrainerWorkload trainerWorkload = TrainerWorkload.builder()
                    .active(dto.active())
                    .username(dto.username())
                    .firstName(dto.firstName())
                    .lastName(dto.lastName())
                    .date(dto.date())
                    .duration(dto.duration())
                    .build();
            trainerWorkload = trainerWorkloadRepository.save(trainerWorkload);
            return trainerWorkload;
        } else if ("DELETE".equalsIgnoreCase(dto.actionType())) {
            trainerWorkloadRepository.deleteByUsernameAndDate(dto.username(), dto.date());
        }
        return null;
    }

    public List<TrainerWorkload> getWorkloads() {
        return trainerWorkloadRepository.findAll();
    }

    public List<TrainerWorkload> getWorkloadsByUsername(String username) throws UserPrincipalNotFoundException {
        Boolean aBoolean = mainServiceClient.existByUsername(username);
        if (!aBoolean) {
            throw new UserPrincipalNotFoundException("User not found with username '%s'".formatted(username));
        }
        return trainerWorkloadRepository.findAllByUsername(username);
    }

    public TrainerWorkload update(String username, TrainerWorkloadUpdateDTO dto) {
        TrainerWorkload firstByUsername = trainerWorkloadRepository.findFirstByUsername(username, 1);
        if (firstByUsername != null) {
            firstByUsername.setActive(dto.active());
            firstByUsername.setFirstName(dto.firstName());
            firstByUsername.setLastName(dto.lastName());
            firstByUsername.setDate(dto.date());
            firstByUsername.setDuration(dto.duration());
            return trainerWorkloadRepository.save(firstByUsername);
        }
        return null;
    }
}
