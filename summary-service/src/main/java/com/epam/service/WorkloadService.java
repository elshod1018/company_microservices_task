package com.epam.service;

import com.epam.dto.TrainerWorkloadDTO;
import com.epam.entity.TrainerWorkload;
import com.epam.repository.TrainerWorkloadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkloadService {
    private final TrainerWorkloadRepository trainerWorkloadRepository;

    @Modifying
    @Transactional
    public TrainerWorkload workload(TrainerWorkloadDTO dto) {
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
}
