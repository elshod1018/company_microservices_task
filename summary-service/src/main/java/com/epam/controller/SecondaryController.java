package com.epam.controller;

import com.epam.dto.TrainerWorkloadUpdateDTO;
import com.epam.entity.TrainerWorkload;
import com.epam.service.WorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/workload")
public class SecondaryController {
    private final WorkloadService workloadService;

    @GetMapping("/summary/all")
    public List<TrainerWorkload> summary() {
        log.info("Getting all workloads");
        return workloadService.getWorkloads();
    }

    @GetMapping("/summary/by-username")
    public List<TrainerWorkload> byUsername(@RequestParam(name = "username") String username) throws UserPrincipalNotFoundException {
        return workloadService.getWorkloadsByUsername(username);
    }

    @PutMapping("/summary/by-username")
    public TrainerWorkload update(@RequestParam(name = "username") String username, @RequestBody TrainerWorkloadUpdateDTO dto) {
        return workloadService.update(username, dto);
    }
}
