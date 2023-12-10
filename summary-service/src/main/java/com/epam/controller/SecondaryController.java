package com.epam.controller;

import com.epam.entity.TrainerWorkload;
import com.epam.service.WorkloadService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/workload")
public class SecondaryController {
    private final WorkloadService workloadService;

    @GetMapping("/summary")
    public List<TrainerWorkload> summary(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        log.info("Trainer workload summary authorization: {}", authorization);
        return workloadService.getWorkloads();
    }
}
